package org.wso2.carbon.identity.provider.mgt.endpoint;

import org.wso2.carbon.identity.application.common.model.IdentityProvider;
import org.wso2.carbon.identity.core.util.IdentityUtil;
import org.wso2.carbon.identity.provider.mgt.endpoint.ExtendedIdentityProvider;
import org.wso2.carbon.identity.provider.mgt.endpoint.dto.IDPListResponseDTO;
import org.wso2.carbon.identity.provider.mgt.endpoint.dto.IdentityProviderDTO;
import org.wso2.carbon.identity.provider.mgt.endpoint.Constants;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static IdentityProviderDTO translateIDP(ExtendedIdentityProvider identityProvider) {

        IdentityProviderDTO identityProviderDTO = new IdentityProviderDTO();
        identityProviderDTO.setId(identityProvider.getUniqueId());
        identityProviderDTO.setName(identityProvider.getIdentityProviderName());
        identityProviderDTO.setDisplayName(identityProvider.getDisplayName());
        identityProviderDTO.setHomeRealmIdentifier(identityProvider.getHomeRealmId());
        identityProviderDTO.setDescription(identityProvider.getIdentityProviderDescription());
        return identityProviderDTO;
    }

    public static IdentityProvider translateIDP(IdentityProviderDTO identityProviderDTO) {

        IdentityProvider identityProvider = new IdentityProvider();
        identityProvider.setIdentityProviderName(identityProviderDTO.getName());
        identityProvider.setDisplayName(identityProviderDTO.getDisplayName());
        identityProvider.setHomeRealmId(identityProviderDTO.getHomeRealmIdentifier());
        identityProvider.setIdentityProviderDescription(identityProviderDTO.getDescription());
        return identityProvider;
    }

    public static IDPListResponseDTO[] translateIDPList(List<ExtendedIdentityProvider> idpList) {

        List<IDPListResponseDTO> idpListResponseDTOList = new ArrayList<>();
        idpList.stream().forEach(idp -> {
            IDPListResponseDTO idpListResponseDTO = new IDPListResponseDTO();
            idpListResponseDTO.setId(idp.getUniqueId());
            idpListResponseDTO.setName(idp.getIdentityProviderName());
            idpListResponseDTO.setDisplayName(idp.getDisplayName());
            idpListResponseDTO.setDescription(idp.getIdentityProviderDescription());
            idpListResponseDTOList.add(idpListResponseDTO);
        });
        return idpListResponseDTOList.toArray(new IDPListResponseDTO[idpListResponseDTOList.size()]);
    }

    public static String decodeIDPId(String encodedId) {

        byte[] bytes = IdentityUtil.base58Decode(encodedId);
        String prefixedId = new String(bytes);
        return prefixedId.replaceFirst(Constants.IDP_UNIQUE_ID_PREFIX, "");
    }

    public static String getIDPLocation(String plainId) {

        String idpUniqueId = Constants.IDP_UNIQUE_ID_PREFIX + plainId;
        String idpLocationURL = IdentityUtil.getServerURL(Constants.IDP_MGT_CONTEXT_PATH + idpUniqueId,
                true, false);
        return idpLocationURL;
    }
}
