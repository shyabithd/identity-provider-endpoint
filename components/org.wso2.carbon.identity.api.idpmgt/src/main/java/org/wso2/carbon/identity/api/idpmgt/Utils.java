package org.wso2.carbon.identity.api.idpmgt;

import org.wso2.carbon.identity.core.util.IdentityUtil;

public class Utils {

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
