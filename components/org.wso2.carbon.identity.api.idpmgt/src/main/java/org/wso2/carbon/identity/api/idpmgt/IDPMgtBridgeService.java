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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.common.model.IdentityProvider;
import org.wso2.carbon.identity.application.common.util.IdentityApplicationConstants;
import org.wso2.carbon.idp.mgt.IdentityProviderManagementException;
import org.wso2.carbon.idp.mgt.IdentityProviderManager;

import java.util.ArrayList;
import java.util.List;

public class IDPMgtBridgeService {

    private static final Log log = LogFactory.getLog(IdentityProviderManager.class);

    private static IDPMgtBridgeService instance = new IDPMgtBridgeService();

    private IdentityProviderManager identityProviderManager = IdentityProviderManager.getInstance();

    private IDPMgtBridgeService() {

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

        // TODO : Remove hard corded tenant domain.
        String tenantDomain = "carbon.super";
        idpID = Utils.decodeIDPId(idpID);
        ExtendedIdentityProvider idpById = getIDPById(idpID);
        if (idpById.getId() == null) {
            throw new IDPMgtBridgeServiceException("403", "Not allowed to delete file based IDP",
                    "Not allowed to delete file based IDP");
        }
        identityProviderManager.deleteIdP(idpById.getIdentityProviderName(), tenantDomain);
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

        // TODO : Remove hard corded tenant domain.
        String tenantDomain = "carbon.super";
        validateIDPParams(identityProvider);
        IdentityProvider oldIDP = identityProviderManager.getIdPByName(identityProvider.getIdentityProviderName(),
                tenantDomain);
        validateIDPName(identityProvider, oldIDP);
        identityProviderManager.addIdP(identityProvider, tenantDomain);
        return identityProviderManager.getIdPByName(identityProvider.getIdentityProviderName(), tenantDomain);
    }

    /**
     * Returns Extended IdentityProvider instance which is corresponding to the given given ID.
     *
     * @param idpID ID of the identity provider
     * @return Extended Identity Provider with givne ID
     * @throws IdentityProviderManagementException IdentityProviderManagementException.
     * @throws IDPMgtBridgeServiceException        IDPMgtBridgeServiceException.
     */
    public ExtendedIdentityProvider getIDPById(String idpID) throws
            IdentityProviderManagementException, IDPMgtBridgeServiceException {

        // TODO : remove hard coded tenant domain.
        String tenantDomain = "carbon.super";
        idpID = Utils.decodeIDPId(idpID);
        IdentityProvider idp = identityProviderManager.getIdPById(idpID, tenantDomain);
        if (idp == null) {
            throw new IDPMgtBridgeServiceException("404", "IDP not found", "IDP not found");
        }

        if (isDefaultIDP(idp) && !StringUtils.equals(IdentityApplicationConstants.DEFAULT_IDP_CONFIG, idpID)) {
            throw new IDPMgtBridgeServiceException("404", "IDP not found", "IDP not found");
        }

        return new ExtendedIdentityProvider(idp);
    }

    /**
     * Get the existing list of identity providers in the system.
     *
     * @return List of existing Identity Providers in the system as Extended Identity Providers.
     * @throws IdentityProviderManagementException IdentityProviderManagementException.
     * @throws IDPMgtBridgeServiceException        IDPMgtBridgeServiceException.
     */
    public List<ExtendedIdentityProvider> getIDPs() throws
            IdentityProviderManagementException, IDPMgtBridgeServiceException {

        // TODO : remove hard coded tenant domain.
        String tenantDomain = "carbon.super";
        List<IdentityProvider> idPs = identityProviderManager.getIdPs(tenantDomain);

        return getExtendedIDPList(idPs);
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
        // TODO : Remove hard corded tenant domain.
        String tenantDomain = "carbon.super";
        validateIDPParams(identityProvider);
        String decodedIDPId = Utils.decodeIDPId(id);
        IdentityProvider oldIDP = identityProviderManager.getIdPById(decodedIDPId, tenantDomain);

        if (isDefaultIDP(identityProvider)) {
            throw new IDPMgtBridgeServiceException("409", "An IDP with given name already exists",
                    "An IDP with given name already exists");
        }
        if (isDefaultIDP(oldIDP)) {
            throw new IDPMgtBridgeServiceException("404", "No existing IDP found with given ID",
                    "No existing IDP found with given ID");
        }

        validateDuplicateIDPs(identityProvider, tenantDomain, decodedIDPId, oldIDP);
        identityProviderManager.updateIdP(oldIDP.getIdentityProviderName(), identityProvider, tenantDomain);
        return identityProviderManager.getIdPByName(identityProvider.getIdentityProviderName(), tenantDomain);
    }

    private void validateDuplicateIDPs(IdentityProvider identityProvider, String tenantDomain, String decodedIDPId,
                                       IdentityProvider oldIDP) throws
            IdentityProviderManagementException, IDPMgtBridgeServiceException {

        if (!hasSameIDPName(identityProvider, oldIDP)) {
            IdentityProvider duplicateIDP = identityProviderManager.getIdPByName(identityProvider
                    .getIdentityProviderName(), tenantDomain);

            if (hasSameIDPName(duplicateIDP, identityProvider) &&
                    !StringUtils.equals(decodedIDPId, duplicateIDP.getId())) {
                throw new IDPMgtBridgeServiceException("409", "An IDP with given name already exists",
                        "An IDP with given name already exists");
            }

        }
    }

    private List<ExtendedIdentityProvider> getExtendedIDPList(List<IdentityProvider> identityProviderList) {

        List<ExtendedIdentityProvider> extendedIdentityProviders = new ArrayList<>();

        for (IdentityProvider idp : identityProviderList) {
            extendedIdentityProviders.add(new ExtendedIdentityProvider(idp));
        }
        return extendedIdentityProviders;
    }

    private void validateIDPParams(IdentityProvider identityProvider) throws IDPMgtBridgeServiceException {

        if (StringUtils.isEmpty(identityProvider.getIdentityProviderName())) {
            throw new IDPMgtBridgeServiceException("400", "Identity Provider name is missing", "Identity Provider " +
                    "name is missing");
        }
    }

    private void validateIDPName(IdentityProvider identityProvider, IdentityProvider oldIDP) throws
            IDPMgtBridgeServiceException {

        if (isDefaultIDP(identityProvider) || hasSameIDPName(identityProvider, oldIDP)) {
            throw new IDPMgtBridgeServiceException("409", "An IDP with given name already exists",
                    "An IDP with given name already exists");
        }
    }

    private boolean hasSameIDPName(IdentityProvider identityProvider, IdentityProvider oldIDP) {

        return StringUtils.equals(identityProvider.getIdentityProviderName(), oldIDP.getIdentityProviderName());
    }

    private boolean isDefaultIDP(IdentityProvider identityProvider) {

        return StringUtils.equals(IdentityApplicationConstants.DEFAULT_IDP_CONFIG,
                identityProvider.getIdentityProviderName());
    }
}
