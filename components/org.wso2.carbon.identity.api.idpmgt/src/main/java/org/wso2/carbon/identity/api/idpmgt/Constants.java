package org.wso2.carbon.identity.api.idpmgt;

public class Constants {

    public static final String IDP_MANAGEMENT_CONFIG_XML = "idp-mgt-config.xml";
    public static final String IDP_UNIQUE_ID_PREFIX = "identityProviderId.";
    public static String IDP_MGT_CONTEXT_PATH = "api/identity/idp-mgt/v1.0/";
    public static final String IDP_SEARCH_LIMIT_PATH = "SearchLimits.IdP";
    public static final String APPLICATION_JSON = "application/json";
    public static final String DEFAULT_RESPONSE_CONTENT_TYPE = APPLICATION_JSON;
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String STATUS_INTERNAL_SERVER_ERROR_MESSAGE_DEFAULT = "Internal server error";
    public static final String STATUS_BAD_REQUEST_MESSAGE_DEFAULT = "Bad Request";

    public enum ErrorMessages {

        ERROR_CODE_RESOURCE_NOT_FOUND("IDP_00001", "No existing IDP found with given ID"),
        ERROR_CODE_IDP_ALREADY_EXIST("IDP_00002", "An IDP with given name already exists"),
        ERROR_CODE_INVALID_IDP("IDP_00003", "Identity Provider name is missing"),
        ERROR_CODE_INVALID_ARGS_FOR_LIMIT_OFFSET("IDP_00004", "Identity Provider name is missing"),
        ERROR_CODE_INVALID_TYPE_RECIEVED("IDP_00006", "Invalid type received"),
        ERROR_CODE_INVALID_FEDERATED_CONFIG("IDP_00007", "Invalid Federated Configuration received"),
        ERROR_CODE_INVALID_ROLE_CONFIG("IDP_00008", "Invalid Role Configuration received"),
        ERROR_CODE_INVALID_PROVISIONING_CONFIG("IDP_00009", "Invalid Provisioning Configuration received"),
        ERROR_CODE_BUILDING_CONFIG("CM_00010", "Error occurred while building configuration from consent-mgt-config" +
                ".xml.");
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
