package org.wso2.carbon.identity.api.idpmgt.endpoint.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.api.idpmgt.ExtendedIdentityProvider;
import org.wso2.carbon.identity.api.idpmgt.IDPMgtBridgeService;
import org.wso2.carbon.identity.api.idpmgt.IDPMgtBridgeServiceException;
import org.wso2.carbon.identity.api.idpmgt.Utils;
import org.wso2.carbon.identity.api.idpmgt.endpoint.EndpointUtils;
import org.wso2.carbon.identity.api.idpmgt.endpoint.IdpsApiService;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.IdPDetailDTO;
import org.wso2.carbon.identity.application.common.model.IdentityProvider;
import org.wso2.carbon.idp.mgt.IdentityProviderManagementException;

import java.util.List;
import javax.ws.rs.core.Response;

public class IdpsApiServiceImpl extends IdpsApiService {

    private static final Log log = LogFactory.getLog(IdpsApiServiceImpl.class);

    private IDPMgtBridgeService idpMgtBridgeService = IDPMgtBridgeService.getInstance();

    @Override
    public Response idpsGet(Integer limit, Integer offset, String query, String accept, String ifNoneMatch, Boolean expand) {

        try {
            List<IdentityProvider> idps = idpMgtBridgeService.getIDPs();
            return Response.ok().entity(EndpointUtils.translateIDPList(idps)).build();
        } catch (IdentityProviderManagementException e) {
            return handleServerErrorResponse(e);
        }
    }

    @Override
    public Response idpsIdDelete(String id, String accept, String ifNoneMatch) {

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
    public Response idpsIdGet(String id, String accept, String ifNoneMatch) {
        try {
            ExtendedIdentityProvider idp = idpMgtBridgeService.getIDPById(id);
            return Response.ok().entity(EndpointUtils.translateIDP(idp)).build();
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

    @Override
    public Response idpsIdPut(String id, IdPDetailDTO body, String contentType) {

        try {
            IdentityProvider translatedIDP = EndpointUtils.translateIDP(body);
            IdentityProvider updatedIDP = idpMgtBridgeService.updateIDP(translatedIDP, id);
            return Response.ok().header("Location", Utils.getIDPLocation(updatedIDP.getId())).build();
        } catch (IDPMgtBridgeServiceException e) {
            return handleBadRequestResponse(e);
        } catch (IdentityProviderManagementException e) {
            return handleServerErrorResponse(e);
        }
    }

    @Override
    public Response idpsPost(IdPDetailDTO body, String contentType) {

        try {
            IdentityProvider translatedIDP = EndpointUtils.translateIDP(body);
            IdentityProvider newIDP = idpMgtBridgeService.addIDP(translatedIDP);
            return Response.ok().header("Location", Utils.getIDPLocation(newIDP.getId())).build();
        } catch (IDPMgtBridgeServiceException e) {
            return Response.status(Integer.parseInt(e.getErrorCode())).build();
        } catch (IdentityProviderManagementException e) {
            return Response.status(Integer.parseInt(e.getErrorCode())).build();
        }
    }
}
