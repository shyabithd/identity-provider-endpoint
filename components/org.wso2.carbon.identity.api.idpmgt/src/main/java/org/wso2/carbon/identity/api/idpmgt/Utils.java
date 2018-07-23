package org.wso2.carbon.identity.api.idpmgt;

import org.apache.commons.lang.StringUtils;
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

    /**
     * This method can be used to generate a ConsentManagementClientException from ConsentConstants.ErrorMessages
     * object when no exception is thrown.
     *
     * @param error ConsentConstants.ErrorMessages.
     * @param data  data to replace if message needs to be replaced.
     * @return ConsentManagementClientException.
     */
    public static IDPMgtBridgeServiceException handleException(Constants.ErrorMessages error,
                                                                         String data) {

        String message;
        if (StringUtils.isNotBlank(data)) {
            message = String.format(error.getMessage(), data);
        } else {
            message = error.getMessage();
        }

        return new IDPMgtBridgeServiceException(message, error.getCode());
    }

}
