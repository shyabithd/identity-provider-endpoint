package org.wso2.carbon.identity.api.idpmgt.endpoint.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.api.idpmgt.IDPMgtBridgeService;
import org.wso2.carbon.identity.api.idpmgt.IDPMgtBridgeServiceException;
import org.wso2.carbon.identity.api.idpmgt.Utils;
import org.wso2.carbon.identity.api.idpmgt.endpoint.EndpointUtils;
import org.wso2.carbon.identity.api.idpmgt.endpoint.IdpsApiService;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.ClaimConfigDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.FederatedAuthenticatorConfigDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.IdPDetailDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.JustInTimeProvisioningConfigDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.PermissionsAndRoleConfigDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.ProvisioningConnectorConfigDTO;
import org.wso2.carbon.identity.application.common.model.ClaimConfig;
import org.wso2.carbon.identity.application.common.model.FederatedAuthenticatorConfig;
import org.wso2.carbon.identity.application.common.model.IdentityProvider;
import org.wso2.carbon.identity.application.common.model.JustInTimeProvisioningConfig;
import org.wso2.carbon.identity.application.common.model.PermissionsAndRoleConfig;
import org.wso2.carbon.identity.application.common.model.ProvisioningConnectorConfig;
import org.wso2.carbon.idp.mgt.IdentityProviderManagementException;
import java.util.List;
import javax.ws.rs.core.Response;

public class IdpsApiServiceImpl extends IdpsApiService {

    private static final Log log = LogFactory.getLog(IdpsApiServiceImpl.class);

    private IDPMgtBridgeService idpMgtBridgeService = IDPMgtBridgeService.getInstance();

    @Override
    public Response idpsGet(Integer limit, Integer offset, String spTenantDomain) {

        try {
            List<IdentityProvider> identityProviders = idpMgtBridgeService.getIDPs(limit, offset);
            return Response.ok().entity(EndpointUtils.translateIDPDetailList(identityProviders)).build();
        } catch (IdentityProviderManagementException e) {
            return handleServerErrorResponse(e);
        } catch (IDPMgtBridgeServiceException e) {
            return handleBadRequestResponse(e);
        }
    }

    @Override
    public Response idpsIdAuthenticatorsGet(String id, Integer limit, Integer offset) {

        try {
            List<FederatedAuthenticatorConfig> federatedAuthenticatorConfigs = idpMgtBridgeService
                    .getAuthenticatorList(id, limit, offset);
            return Response.ok().entity(EndpointUtils.createFederatorAuthenticatorDTOList
                    (federatedAuthenticatorConfigs)).build();
        } catch (IDPMgtBridgeServiceException e) {
            return handleBadRequestResponse(e);
        } catch (IdentityProviderManagementException e) {
            return handleServerErrorResponse(e);
        }
    }

    @Override
    public Response idpsIdAuthenticatorsPut(String id, FederatedAuthenticatorConfigDTO body) {

        try {
            FederatedAuthenticatorConfig receivedAuthenticatorConfig = EndpointUtils.createDefaultAuthenticator(body);
            idpMgtBridgeService.updateAuthenticator(receivedAuthenticatorConfig, id);
            return Response.ok().build();
        } catch (IDPMgtBridgeServiceException e) {
            return handleBadRequestResponse(e);
        } catch (IdentityProviderManagementException e) {
            return handleServerErrorResponse(e);
        }
    }

    @Override
    public Response idpsIdClaimsGet(String id) {

        try {
            IdentityProvider idp = idpMgtBridgeService.getIDPById(id);
            return Response.ok().entity(EndpointUtils.createClaimConfigDTO(idp.getClaimConfig())).build();
        } catch (IDPMgtBridgeServiceException e) {
            return handleBadRequestResponse(e);
        } catch (IdentityProviderManagementException e) {
            return handleServerErrorResponse(e);
        }
    }

    @Override
    public Response idpsIdClaimsPut(String id, ClaimConfigDTO body) {

        try {
            ClaimConfig claimConfig = EndpointUtils.createClaimConfig(body);
            idpMgtBridgeService.updateClaimConfiguration(claimConfig, id);
            return Response.ok().build();
        } catch (IDPMgtBridgeServiceException e) {
            return handleBadRequestResponse(e);
        } catch (IdentityProviderManagementException e) {
            return handleServerErrorResponse(e);
        }
    }

    @Override
    public Response idpsIdDelete(String id) {

        try {
            idpMgtBridgeService.deleteIDP(id);
            return Response.ok().build();
        } catch (IDPMgtBridgeServiceException e) {
            return handleBadRequestResponse(e);
        } catch (IdentityProviderManagementException e) {
            return handleServerErrorResponse(e);
        }
    }

    @Override
    public Response idpsIdGet(String id) {

        try {
            IdentityProvider idp = idpMgtBridgeService.getIDPById(id);
            return Response.ok().entity(EndpointUtils.translateIDPToIDPDetail(idp)).build();
        } catch (IDPMgtBridgeServiceException e) {
            return handleBadRequestResponse(e);
        } catch (IdentityProviderManagementException e) {
            return handleServerErrorResponse(e);
        }
    }

    @Override
    public Response idpsIdJitProvisioningGet(String id) {

        try {
            IdentityProvider idp = idpMgtBridgeService.getIDPById(id);
            return Response.ok().entity(EndpointUtils.createJustinTimeProvisioningConfigDTO(idp
                    .getJustInTimeProvisioningConfig())).build();
        } catch (IDPMgtBridgeServiceException e) {
            return handleBadRequestResponse(e);
        } catch (IdentityProviderManagementException e) {
            return handleServerErrorResponse(e);
        }
    }

    @Override
    public Response idpsIdJitProvisioningPut(String id, JustInTimeProvisioningConfigDTO body) {

        try {
            JustInTimeProvisioningConfig justInTimeProvisioningConfig = EndpointUtils
                    .createJustinTimeProvisioningConfig(body);
            idpMgtBridgeService.updateJITProvisioningConfig(justInTimeProvisioningConfig, id);
            return Response.ok().build();
        } catch (IDPMgtBridgeServiceException e) {
            return handleBadRequestResponse(e);
        } catch (IdentityProviderManagementException e) {
            return handleServerErrorResponse(e);
        }
    }

    @Override
    public Response idpsIdOutboundConnectorsGet(String id, Integer limit, Integer offset) {

        try {
            List<ProvisioningConnectorConfig> provisioningConnectorConfigs = idpMgtBridgeService
                    .getOutboundConnectorList(id, limit, offset);
            return Response.ok().entity(EndpointUtils.createProvisioningConnectorConfigDTOs(
                    provisioningConnectorConfigs)).build();
        } catch (IDPMgtBridgeServiceException e) {
            return handleBadRequestResponse(e);
        } catch (IdentityProviderManagementException e) {
            return handleServerErrorResponse(e);
        }
    }

    @Override
    public Response idpsIdOutboundConnectorsPut(String id, ProvisioningConnectorConfigDTO body) {

        try {
            ProvisioningConnectorConfig provisioningConnectorConfig = EndpointUtils
                    .createProvisioningConnectorConfig(body);
            idpMgtBridgeService.updateProvisioningConnectorConfig(provisioningConnectorConfig, id);
            return Response.ok().build();
        } catch (IDPMgtBridgeServiceException e) {
            return handleBadRequestResponse(e);
        } catch (IdentityProviderManagementException e) {
            return handleServerErrorResponse(e);
        }
    }

    @Override
    public Response idpsIdPut(String id, IdPDetailDTO body) {

        try {
            IdentityProvider translatedIDP = EndpointUtils.translateIDPDetailToIDP(body);
            IdentityProvider updatedIDP = idpMgtBridgeService.updateIDP(translatedIDP, id);
            return Response.ok().header("Location", Utils.getIDPLocation(updatedIDP.getId())).build();
        } catch (IDPMgtBridgeServiceException e) {
            return handleBadRequestResponse(e);
        } catch (IdentityProviderManagementException e) {
            return handleServerErrorResponse(e);
        }
    }

    @Override
    public Response idpsIdRolesGet(String id) {

        try {
            IdentityProvider idp = idpMgtBridgeService.getIDPById(id);
            return Response.ok().entity(EndpointUtils.createPermissionAndRoleConfigDTO(idp.getPermissionAndRoleConfig
                    ())).build();
        } catch (IDPMgtBridgeServiceException e) {
            return handleBadRequestResponse(e);
        } catch (IdentityProviderManagementException e) {
            return handleServerErrorResponse(e);
        }
    }

    @Override
    public Response idpsIdRolesPut(String id, PermissionsAndRoleConfigDTO body) {

        try {
            PermissionsAndRoleConfig permissionsAndRoleConfig = EndpointUtils.createPermissionAndRoleConfig(body);
            idpMgtBridgeService.updateRoles(permissionsAndRoleConfig, id);
            return Response.ok().build();
        } catch (IDPMgtBridgeServiceException e) {
            return handleBadRequestResponse(e);
        } catch (IdentityProviderManagementException e) {
            return handleServerErrorResponse(e);
        }
    }


    @Override
    public Response idpsPost(IdPDetailDTO body) {

        try {
            IdentityProvider translatedIDP = EndpointUtils.translateIDPDetailToIDP(body);
            IdentityProvider newIDP = idpMgtBridgeService.addIDP(translatedIDP);
            return Response.ok().header("Location", Utils.getIDPLocation(newIDP.getId())).build();
        } catch (IDPMgtBridgeServiceException e) {
            return handleBadRequestResponse(e);
        } catch (IdentityProviderManagementException e) {
            return handleServerErrorResponse(e);
        }
    }


    private Response handleServerErrorResponse(IdentityProviderManagementException e) {

        throw EndpointUtils.buildInternalServerErrorException(e.getErrorCode(), log, e);
    }

    private Response handleBadRequestResponse(IDPMgtBridgeServiceException e) {

        throw EndpointUtils.buildBadRequestException(e.getMessage(), e.getErrorCode(), log, e);
    }
}
