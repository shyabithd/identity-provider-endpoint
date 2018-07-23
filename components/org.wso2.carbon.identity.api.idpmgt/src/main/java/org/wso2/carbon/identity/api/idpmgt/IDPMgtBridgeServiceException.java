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

import org.wso2.carbon.identity.base.IdentityException;

public class IDPMgtBridgeServiceException extends IdentityException {

    private String errorCode;

    public IDPMgtBridgeServiceException(String message) {

        super(message);
    }

    public IDPMgtBridgeServiceException(String errorCode, String message) {

        super(errorCode, message);
        this.errorCode = errorCode;
    }

    public IDPMgtBridgeServiceException(String message, Throwable cause) {

        super(message, cause);
    }

    public IDPMgtBridgeServiceException(String errorCode, String message, Throwable cause) {

        super(errorCode, message, cause);
    }

    public String getErrorCode() {

        return this.errorCode;
    }
}
