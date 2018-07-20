package org.wso2.carbon.identity.api.idpmgt.endpoint;

import org.wso2.carbon.identity.api.idpmgt.Constants;
import org.wso2.carbon.identity.api.idpmgt.ExtendedIdentityProvider;
import org.wso2.carbon.identity.api.idpmgt.IDPMgtBridgeServiceException;
import org.wso2.carbon.identity.api.idpmgt.endpoint.Exceptions.BadRequestException;
import org.wso2.carbon.identity.api.idpmgt.endpoint.Exceptions.InternalServerErrorException;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.ErrorDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.IdPDetailDTO;
import org.wso2.carbon.identity.application.common.model.IdentityProvider;
import org.apache.commons.logging.Log;
import org.wso2.carbon.idp.mgt.IdentityProviderManagementException;

import java.util.ArrayList;
import java.util.List;

public class EndpointUtils {

    public static IdPDetailDTO translateIDP(ExtendedIdentityProvider identityProvider) {

        IdPDetailDTO identityProviderDTO = new IdPDetailDTO();
        identityProviderDTO.setId(identityProvider.getUniqueId());
        identityProviderDTO.setDisplayName(identityProvider.getIdentityProviderName());
        identityProviderDTO.setDisplayName(identityProvider.getDisplayName());
        identityProviderDTO.setHomeRealmId(identityProvider.getHomeRealmId());
        return identityProviderDTO;
    }

    public static IdentityProvider translateIDP(IdPDetailDTO identityProviderDTO) {

        IdentityProvider identityProvider = new IdentityProvider();
        identityProvider.setIdentityProviderName(identityProviderDTO.getDisplayName());
        identityProvider.setDisplayName(identityProviderDTO.getDisplayName());
        identityProvider.setHomeRealmId(identityProviderDTO.getHomeRealmId());
        return identityProvider;
    }

    public static List<IdPDetailDTO> translateIDPList(List<IdentityProvider> idpList) {

       List<IdPDetailDTO> idpListResponseDTOList = new ArrayList<>();
       for ( IdentityProvider idp : idpList) {
           IdPDetailDTO idpListResponseDTO = new IdPDetailDTO();
           idpListResponseDTO.setId(idp.getId());
           idpListResponseDTO.setDisplayName(idp.getIdentityProviderName());
           idpListResponseDTO.setDisplayName(idp.getDisplayName());
           idpListResponseDTOList.add(idpListResponseDTO);
       }

       return idpListResponseDTOList;
    }

    public static InternalServerErrorException buildInternalServerErrorException(String errorCode,
                                                                                 Log log, IdentityProviderManagementException exception) {
        ErrorDTO errorDTO = getErrorDTO(Constants.STATUS_INTERNAL_SERVER_ERROR_MESSAGE_DEFAULT,
                Constants.STATUS_INTERNAL_SERVER_ERROR_MESSAGE_DEFAULT, errorCode);
        logError(log, exception);
        return new InternalServerErrorException(errorDTO);
    }

    public static BadRequestException buildBadRequestException(String description, String code,
                                                               Log log, IDPMgtBridgeServiceException e) {

        ErrorDTO errorDTO  =  getErrorDTO(Constants.STATUS_BAD_REQUEST_MESSAGE_DEFAULT, description, code);
        logDebug(log, e);
        return new BadRequestException(errorDTO);
    }

    private static void logDebug(Log log, Throwable throwable) {

        if (log.isDebugEnabled()) {
            log.debug(Constants.STATUS_BAD_REQUEST_MESSAGE_DEFAULT, throwable);
        }
    }
    private static void logError(Log log, Throwable throwable) {

        log.error(throwable.getMessage(), throwable);
    }

    private static ErrorDTO getErrorDTO(String message, String description, String code) {

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setCode(code);
        errorDTO.setMessage(message);
        errorDTO.setDescription(description);
        return errorDTO;
    }
}
