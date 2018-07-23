package org.wso2.carbon.identity.api.idpmgt;

public class Constants {

    public static String IDP_UNIQUE_ID_PREFIX = "identityProviderId.";
    public static String IDP_MGT_CONTEXT_PATH = "api/identity/idp-mgt/v1.0/";
    public static final String APPLICATION_JSON = "application/json";
    public static final String DEFAULT_RESPONSE_CONTENT_TYPE = APPLICATION_JSON;
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String STATUS_INTERNAL_SERVER_ERROR_MESSAGE_DEFAULT = "Internal server error";
    public static final String STATUS_INTERNAL_SERVER_ERROR_DESCRIPTION_DEFAULT = "The server encountered "
            + "an internal error. Please contact administrator.";
    public static final String STATUS_BAD_REQUEST_MESSAGE_DEFAULT = "Bad Request";
    public static final String PURPOSE_SEARCH_LIMIT_PATH = "SearchLimits.Purpose";

    public enum ErrorMessages {

        ERROR_CODE_RESOURCE_NOT_FOUND("IDP_00001", "No existing IDP found with given ID"),
        ERROR_CODE_IDP_ALREADY_EXIST("IDP_00002", "An IDP with given name already exists"),
        ERROR_CODE_INVALID_IDP("IDP_00001", "Identity Provider name is missing");
        private final String code;
        private final String message;

        ErrorMessages(String code, String message) {

            this.code = code;
            this.message = message;
        }

        public String getCode() {

            return code;
        }

        public String getMessage() {

            return message;
        }

        @Override
        public String toString() {

            return code + " : " + message;
        }
    }
}
