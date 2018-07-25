/*
 * Copyright (c) 2018 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.api.idpmgt;

import javafx.util.Pair;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.application.common.model.ClaimConfig;
import org.wso2.carbon.identity.application.common.model.FederatedAuthenticatorConfig;
import org.wso2.carbon.identity.application.common.model.IdentityProvider;
import org.wso2.carbon.identity.application.common.model.JustInTimeProvisioningConfig;
import org.wso2.carbon.identity.application.common.model.PermissionsAndRoleConfig;
import org.wso2.carbon.identity.application.common.model.ProvisioningConnectorConfig;
import org.wso2.carbon.identity.application.common.util.IdentityApplicationConstants;
import org.wso2.carbon.idp.mgt.IdentityProviderManagementException;
import org.wso2.carbon.idp.mgt.IdentityProviderManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IDPMgtBridgeService {

    private static final Log log = LogFactory.getLog(IdentityProviderManager.class);

    private static IDPMgtBridgeService instance = new IDPMgtBridgeService();

    private IdentityProviderManager identityProviderManager = IdentityProviderManager.getInstance();

    private IdPConfigParser idPConfigParser;

    private static final int DEFAULT_SEARCH_LIMIT = 100;

    private IDPMgtBridgeService() {

        idPConfigParser = new IdPConfigParser();
    }

    /**
     * @return IDPMgtBridgeService
     */
    public static IDPMgtBridgeService getInstance() {

        return instance;
    }

    /**
     * Deletes and existing IDP.
     *
     * @param idpID Identity Provider ID.
     * @throws IdentityProviderManagementException IdentityProviderManagementException.
     * @throws IDPMgtBridgeServiceException        IDPMgtBridgeServiceException.
     */
    public void deleteIDP(String idpID) throws IdentityProviderManagementException, IDPMgtBridgeServiceException {

        String tenantDomain = PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantDomain();
        IdentityProvider idpById = getIDPById(idpID);
        identityProviderManager.deleteIdP(idpById.getIdentityProviderName(), tenantDomain);

        if (log.isDebugEnabled()) {
            log.debug(String.format("Idp is deleted: %s", idpById));
        }
    }

    /**
     * Adds an IDP.
     *
     * @param identityProvider Identity Provider which needs to be added.
     * @return The identity provider which was added.
     * @throws IdentityProviderManagementException IdentityProviderManagementException.
     * @throws IDPMgtBridgeServiceException        IDPMgtBridgeServiceException.
     */
    public IdentityProvider addIDP(IdentityProvider identityProvider) throws
            IdentityProviderManagementException, IDPMgtBridgeServiceException {

        String tenantDomain = PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantDomain();
        validateIDPParams(identityProvider);
        IdentityProvider oldIDP = identityProviderManager.getIdPByName(identityProvider.getIdentityProviderName(),
                tenantDomain);
        validateIDPName(identityProvider, oldIDP);
        identityProviderManager.addIdP(identityProvider, tenantDomain);
        if (log.isDebugEnabled()) {
            log.debug(String.format("Idp is added: %s", identityProvider.getIdentityProviderName()));
        }
        return identityProviderManager.getIdPByName(identityProvider.getIdentityProviderName(), tenantDomain);
    }

    /**
     * Returns IdentityProvider instance which is corresponding to the given given ID.
     *
     * @param idpID ID of the identity provider
     * @return Identity Provider with givne ID
     * @throws IdentityProviderManagementException IdentityProviderManagementException.
     * @throws IDPMgtBridgeServiceException        IDPMgtBridgeServiceException.
     */
    public IdentityProvider getIDPById(String idpID) throws
            IdentityProviderManagementException, IDPMgtBridgeServiceException {

        String tenantDomain = PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantDomain();
        String idpid = Utils.decodeIDPId(idpID);
        IdentityProvider idp = identityProviderManager.getIdPById(idpid, tenantDomain);
        if (idp == null) {
            throw Utils.handleException(Constants.ErrorMessages.ERROR_CODE_RESOURCE_NOT_FOUND, null);
        }

        if (isDefaultIDP(idp) && !StringUtils.equals(IdentityApplicationConstants.DEFAULT_IDP_CONFIG, idpID)) {
            throw Utils.handleException(Constants.ErrorMessages.ERROR_CODE_RESOURCE_NOT_FOUND, null);
        }

        return idp;
    }

    /**
     * Function returns the set of authenticators for a given IDP
     *
     * @param idpID  id of the idp
     * @param limit  limit of the query
     * @param offset starting index of the query
     * @return list of authenticators which satisfy the query parameters
     * @throws IdentityProviderManagementException throws
     * @throws IDPMgtBridgeServiceException        throws
     */
    public List<FederatedAuthenticatorConfig> getAuthenticatorList(String idpID, Integer limit, Integer offset) throws
            IdentityProviderManagementException, IDPMgtBridgeServiceException {

        IdentityProvider idp = getIDPById(idpID);
        List<FederatedAuthenticatorConfig> federatedAuthenticatorConfigs = Arrays.asList(idp
                .getFederatedAuthenticatorConfigs());

        Pair<Integer, Integer> indexValue = paginationList(federatedAuthenticatorConfigs.size(), limit, offset);
        return federatedAuthenticatorConfigs.subList(indexValue.getKey(), indexValue.getValue());
    }

    /**
     * Function returns the set of provision connectors for a given IDP
     *
     * @param idpID  id of the idp
     * @param limit  limit of the query
     * @param offset starting index of the query
     * @return list of authenticators which satisfy the query parameters
     * @throws IdentityProviderManagementException throws
     * @throws IDPMgtBridgeServiceException        throws
     */
    public List<ProvisioningConnectorConfig> getOutboundConnectorList(String idpID, Integer limit, Integer offset)
            throws
            IdentityProviderManagementException, IDPMgtBridgeServiceException {

        IdentityProvider idp = getIDPById(idpID);
        List<ProvisioningConnectorConfig> provisioningConnectorConfigs = Arrays.asList(idp
                .getProvisioningConnectorConfigs());

        Pair<Integer, Integer> indexValue = paginationList(provisioningConnectorConfigs.size(), limit, offset);
        return provisioningConnectorConfigs.subList(indexValue.getKey(), indexValue.getValue());
    }

    /**
     * Get the existing list of identity providers in the system.
     *
     * @return List of existing Identity Providers in the system as Identity Providers.
     * @throws IdentityProviderManagementException IdentityProviderManagementException.
     */
    public List<IdentityProvider> getIDPs(Integer limit, Integer offset) throws
            IdentityProviderManagementException, IDPMgtBridgeServiceException {

        String tenantDomain = PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantDomain();
        List<IdentityProvider> identityProviders = identityProviderManager.getIdPs(tenantDomain);

        Pair<Integer, Integer> indexValue = paginationList(identityProviders.size(), limit, offset);
        return identityProviders.subList(indexValue.getKey(), indexValue.getValue());
    }

    private int getDefaultLimitFromConfig() {

        int limit = DEFAULT_SEARCH_LIMIT;

        if (idPConfigParser.getConfiguration().get(Constants.IDP_SEARCH_LIMIT_PATH) != null) {
            limit = Integer.parseInt(idPConfigParser.getConfiguration()
                    .get(Constants.IDP_SEARCH_LIMIT_PATH).toString());
        }
        return limit;
    }

    private Pair<Integer, Integer> paginationList(Integer listSize, Integer limit, Integer offset) throws
            IDPMgtBridgeServiceException {

        if (limit == null) {
            limit = getDefaultLimitFromConfig();
        }

        if (offset == null) {
            offset = 0;
        }

        validatePaginationParameters(limit, offset);

        int endIndex;
        if (listSize <= (limit + offset)) {
            endIndex = listSize;
        } else {
            endIndex = (limit + offset);
        }
        if (listSize < offset) {
            endIndex = 0;
            offset = 0;
        }
        return new Pair<>(offset, endIndex);
    }

    /**
     * Updates an existing IDP.
     *
     * @param identityProvider Identity Provider which needs to be updated.
     * @param id               Identity Provider ID.
     * @return Updated Identity Provider.
     * @throws IDPMgtBridgeServiceException        IDPMgtBridgeServiceException.
     * @throws IdentityProviderManagementException IdentityProviderManagementException.
     */
    public IdentityProvider updateIDP(IdentityProvider identityProvider, String id) throws IDPMgtBridgeServiceException,
            IdentityProviderManagementException {

        String tenantDomain = PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantDomain();
        validateIDPParams(identityProvider);
        String decodedIDPId = Utils.decodeIDPId(id);
        IdentityProvider oldIDP = identityProviderManager.getIdPById(decodedIDPId, tenantDomain);

        if (isDefaultIDP(identityProvider)) {
            throw Utils.handleException(Constants.ErrorMessages.ERROR_CODE_IDP_ALREADY_EXIST, null);
        }
        if (isDefaultIDP(oldIDP)) {
            throw Utils.handleException(Constants.ErrorMessages.ERROR_CODE_RESOURCE_NOT_FOUND, null);
        }

        validateDuplicateIDPs(identityProvider, tenantDomain, decodedIDPId, oldIDP);
        identityProviderManager.updateIdP(oldIDP.getIdentityProviderName(), identityProvider, tenantDomain);
        if (log.isDebugEnabled()) {
            log.debug(String.format("Idp is updated: %s", identityProvider.getIdentityProviderName()));
        }
        return identityProviderManager.getIdPByName(identityProvider.getIdentityProviderName(), tenantDomain);
    }

    /**
     * @param federatedAuthenticatorConfig federated authenticator that needs to be added
     * @param id                           id of the IDP
     * @throws IdentityProviderManagementException throws an IdentityProviderManagementException exception
     * @throws IDPMgtBridgeServiceException        throws an IDPMgtBridgeServiceException exception
     */
    public void updateAuthenticator(FederatedAuthenticatorConfig federatedAuthenticatorConfig, String id) throws
            IdentityProviderManagementException, IDPMgtBridgeServiceException {

        validateFederatedAuthenticatorConfig(federatedAuthenticatorConfig);
        IdentityProvider idp = getIDPById(id);
        FederatedAuthenticatorConfig[] federatedAuthenticatorConfigs = idp.getFederatedAuthenticatorConfigs();
        List<FederatedAuthenticatorConfig> federatedAuthenticatorConfigsAsList = Arrays.asList
                (federatedAuthenticatorConfigs);
        List<FederatedAuthenticatorConfig> updatedList = new ArrayList<>(federatedAuthenticatorConfigsAsList);
        updatedList.add(federatedAuthenticatorConfig);
        idp.setFederatedAuthenticatorConfigs(updatedList.toArray(new
                FederatedAuthenticatorConfig[0]));
        updateIDP(idp, id);
        if (log.isDebugEnabled()) {
            log.debug(String.format("Authentication: %s is added to IDP: %s", federatedAuthenticatorConfig
                    .getDisplayName(), idp.getIdentityProviderName()));
        }
    }

    private void validateFederatedAuthenticatorConfig(FederatedAuthenticatorConfig federatedAuthenticatorConfig) throws
            IDPMgtBridgeServiceException {

        if (federatedAuthenticatorConfig == null) {
            throw Utils.handleException(Constants.ErrorMessages.ERROR_CODE_INVALID_TYPE_RECIEVED, null);
        }

        if (StringUtils.isEmpty(federatedAuthenticatorConfig.getName())) {
            throw Utils.handleException(Constants.ErrorMessages.ERROR_CODE_INVALID_FEDERATED_CONFIG, null);
        }
    }

    /**
     * @param receivedClaimConfig claim configuration that needs to be added
     * @param id                  id of the IDP
     * @throws IdentityProviderManagementException throws an IdentityProviderManagementException exception
     * @throws IDPMgtBridgeServiceException        throws an IDPMgtBridgeServiceException exception
     */
    public void updateClaimConfiguration(ClaimConfig receivedClaimConfig, String id) throws
            IdentityProviderManagementException, IDPMgtBridgeServiceException {

        validateClaimConfig(receivedClaimConfig);
        IdentityProvider idp = getIDPById(id);
        idp.setClaimConfig(receivedClaimConfig);
        updateIDP(idp, id);

        if (log.isDebugEnabled()) {
            log.debug(String.format("Claim: %s is added to IDP: %s", receivedClaimConfig
                    .getUserClaimURI(), idp.getIdentityProviderName()));
        }
    }

    private void validateClaimConfig(ClaimConfig receivedClaimConfig) throws IDPMgtBridgeServiceException {

        if (receivedClaimConfig == null) {
            throw Utils.handleException(Constants.ErrorMessages.ERROR_CODE_INVALID_TYPE_RECIEVED, null);
        }
    }

    /**
     * @param permissionsAndRoleConfig role configuration that needs to be added
     * @param id                       id of the IDP
     * @throws IdentityProviderManagementException throws an IdentityProviderManagementException exception
     * @throws IDPMgtBridgeServiceException        throws an IDPMgtBridgeServiceException exception
     */
    public void updateRoles(PermissionsAndRoleConfig permissionsAndRoleConfig, String id) throws
            IdentityProviderManagementException, IDPMgtBridgeServiceException {

        validateRoles(permissionsAndRoleConfig);
        IdentityProvider idp = getIDPById(id);
        idp.setPermissionAndRoleConfig(permissionsAndRoleConfig);
        updateIDP(idp, id);

        if (log.isDebugEnabled()) {
            log.debug(String.format("New permission set is added to IDP: %s", idp.getIdentityProviderName()));
        }
    }

    private void validateRoles(PermissionsAndRoleConfig permissionsAndRoleConfig) throws IDPMgtBridgeServiceException {

        if (permissionsAndRoleConfig == null) {
            throw Utils.handleException(Constants.ErrorMessages.ERROR_CODE_INVALID_TYPE_RECIEVED, null);
        }

        if (ArrayUtils.isEmpty(permissionsAndRoleConfig.getIdpRoles()) || ArrayUtils.isEmpty(permissionsAndRoleConfig
                .getRoleMappings())) {
            throw Utils.handleException(Constants.ErrorMessages.ERROR_CODE_INVALID_ROLE_CONFIG, null);
        }
    }

    /**
     * @param justInTimeProvisioningConfig JIT provisioning configuration that needs to be added
     * @param id                           id of the IDP
     * @throws IdentityProviderManagementException throws an IdentityProviderManagementException exception
     * @throws IDPMgtBridgeServiceException        throws an IDPMgtBridgeServiceException exception
     */
    public void updateJITProvisioningConfig(JustInTimeProvisioningConfig justInTimeProvisioningConfig, String id) throws
            IdentityProviderManagementException, IDPMgtBridgeServiceException {

        IdentityProvider idp = getIDPById(id);
        idp.setJustInTimeProvisioningConfig(justInTimeProvisioningConfig);
        updateIDP(idp, id);

        if (log.isDebugEnabled()) {
            log.debug(String.format("New JIT provisioning set: %s is added to IDP: %s", justInTimeProvisioningConfig
                    .getUserStoreClaimUri(), idp.getIdentityProviderName()));
        }
    }

    /**
     * @param provisioningConnectorConfig provisioning connector configuration that needs to be added
     * @param id                          id of the IDP
     * @throws IdentityProviderManagementException throws an IdentityProviderManagementException exception
     * @throws IDPMgtBridgeServiceException        throws an IDPMgtBridgeServiceException exception
     */
    public void updateProvisioningConnectorConfig(ProvisioningConnectorConfig provisioningConnectorConfig, String id) throws
            IdentityProviderManagementException, IDPMgtBridgeServiceException {

        validateProvisioningConf(provisioningConnectorConfig);
        IdentityProvider idp = getIDPById(id);
        ProvisioningConnectorConfig[] provisioningConnectorConfigs = idp.getProvisioningConnectorConfigs();
        List<ProvisioningConnectorConfig> provisioningConnectorConfigsList = Arrays.asList
                (provisioningConnectorConfigs);
        List<ProvisioningConnectorConfig> updatedList = new ArrayList<>(provisioningConnectorConfigsList);
        updatedList.add(provisioningConnectorConfig);
        idp.setProvisioningConnectorConfigs(updatedList.toArray(new
                ProvisioningConnectorConfig[0]));
        updateIDP(idp, id);

        if (log.isDebugEnabled()) {
            log.debug(String.format("New provisioning connector: %s is added to IDP: %s",
                    provisioningConnectorConfig.getName(), idp.getIdentityProviderName()));
        }
    }

    private void validateProvisioningConf(ProvisioningConnectorConfig provisioningConnectorConfig) throws
            IDPMgtBridgeServiceException {

        if (provisioningConnectorConfig == null) {
            throw Utils.handleException(Constants.ErrorMessages.ERROR_CODE_INVALID_TYPE_RECIEVED, null);
        }

        if (StringUtils.isEmpty(provisioningConnectorConfig.getName())) {
            throw Utils.handleException(Constants.ErrorMessages.ERROR_CODE_INVALID_PROVISIONING_CONFIG, null);
        }
    }

    private void validateDuplicateIDPs(IdentityProvider identityProvider, String tenantDomain, String decodedIDPId,
                                       IdentityProvider oldIDP) throws
            IdentityProviderManagementException, IDPMgtBridgeServiceException {

        if (!hasSameIDPName(identityProvider, oldIDP)) {
            IdentityProvider duplicateIDP = identityProviderManager.getIdPByName(identityProvider
                    .getIdentityProviderName(), tenantDomain);

            if (hasSameIDPName(duplicateIDP, identityProvider) &&
                    !StringUtils.equals(decodedIDPId, duplicateIDP.getId())) {
                throw Utils.handleException(Constants.ErrorMessages.ERROR_CODE_IDP_ALREADY_EXIST, null);
            }
        }
    }

    private void validateIDPParams(IdentityProvider identityProvider) throws IDPMgtBridgeServiceException {

        if (StringUtils.isEmpty(identityProvider.getIdentityProviderName())) {
            throw Utils.handleException(Constants.ErrorMessages.ERROR_CODE_INVALID_IDP, null);
        }
    }

    private void validateIDPName(IdentityProvider identityProvider, IdentityProvider oldIDP) throws
            IDPMgtBridgeServiceException {

        if (isDefaultIDP(identityProvider) || hasSameIDPName(identityProvider, oldIDP)) {
            throw Utils.handleException(Constants.ErrorMessages.ERROR_CODE_IDP_ALREADY_EXIST, null);
        }
    }

    private boolean hasSameIDPName(IdentityProvider identityProvider, IdentityProvider oldIDP) {

        return StringUtils.equals(identityProvider.getIdentityProviderName(), oldIDP.getIdentityProviderName());
    }

    private boolean isDefaultIDP(IdentityProvider identityProvider) {

        return StringUtils.equals(IdentityApplicationConstants.DEFAULT_IDP_CONFIG,
                identityProvider.getIdentityProviderName());
    }

    private void validatePaginationParameters(int limit, int offset) throws IDPMgtBridgeServiceException {

        if (limit < 0 || offset < 0) {
            throw Utils.handleException(Constants.ErrorMessages.ERROR_CODE_INVALID_ARGS_FOR_LIMIT_OFFSET, null);
        }
    }
}
