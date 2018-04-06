package org.wso2.carbon.identity.api.idpmgt.endpoint.impl;

import org.wso2.carbon.identity.api.idpmgt.ExtendedIdentityProvider;
import org.wso2.carbon.identity.api.idpmgt.IDPMgtBridgeService;
import org.wso2.carbon.identity.api.idpmgt.IDPMgtBridgeServiceException;
import org.wso2.carbon.identity.api.idpmgt.Utils;
import org.wso2.carbon.identity.api.idpmgt.endpoint.IdpsApiService;
import org.wso2.carbon.identity.api.idpmgt.endpoint.EndpointUtils;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.IdentityProviderDTO;
import org.wso2.carbon.identity.application.common.model.IdentityProvider;
import org.wso2.carbon.idp.mgt.IdentityProviderManagementException;

import java.util.List;
import javax.ws.rs.core.Response;

public class IdpsApiServiceImpl extends IdpsApiService {

    private IDPMgtBridgeService idpMgtBridgeService = IDPMgtBridgeService.getInstance();

    @Override
    public Response idpsGet(String id, Integer limit, Integer offset) {

        try {
            List<ExtendedIdentityProvider> idps = idpMgtBridgeService.getIDPs();
            return Response.ok().entity(EndpointUtils.translateIDPList(idps)).build();
        } catch (IdentityProviderManagementException e) {
            return Response.serverError().build();
        } catch (IDPMgtBridgeServiceException e) {
            return Response.status(Integer.parseInt(e.getErrorCode())).build();
        }
    }

    @Override
    public Response idpsIdDelete(String id) {

        try {
            idpMgtBridgeService.deleteIDP(id);
            Response.ok().build();
        } catch (IdentityProviderManagementException e) {
            return Response.serverError().build();
        } catch (IDPMgtBridgeServiceException e) {
            return Response.status(Integer.parseInt(e.getErrorCode())).build();
        }
        return Response.ok().build();
    }

    @Override
    public Response idpsIdGet(String id) {

        try {
            ExtendedIdentityProvider idp = idpMgtBridgeService.getIDPById(id);
            return Response.ok().entity(EndpointUtils.translateIDP(idp)).build();
        } catch (IdentityProviderManagementException e) {
            return Response.serverError().build();
        } catch (IDPMgtBridgeServiceException e) {
            return Response.status(Integer.parseInt(e.getErrorCode())).build();
        }
    }

    @Override
    public Response idpsIdPut(String id, IdentityProviderDTO identityProvider) {

        try {
            IdentityProvider translatedIDP = EndpointUtils.translateIDP(identityProvider);
            IdentityProvider updatedIDP = idpMgtBridgeService.updateIDP(translatedIDP, id);
            return Response.ok().header("Location", Utils.getIDPLocation(updatedIDP.getId())).build();
        } catch (IdentityProviderManagementException e) {
            return Response.serverError().build();
        } catch (IDPMgtBridgeServiceException e) {
            return Response.status(Integer.parseInt(e.getErrorCode())).build();
        }
    }

    @Override
    public Response idpsPost(IdentityProviderDTO identityProvider) {

        try {
            IdentityProvider translatedIDP = EndpointUtils.translateIDP(identityProvider);
            IdentityProvider newIDP = idpMgtBridgeService.addIDP(translatedIDP);
            return Response.ok().header("Location", Utils.getIDPLocation(newIDP.getId())).build();
        } catch (IdentityProviderManagementException e) {
            return Response.serverError().build();
        } catch (IDPMgtBridgeServiceException e) {
            return Response.status(Integer.parseInt(e.getErrorCode())).build();
        }
    }
}
