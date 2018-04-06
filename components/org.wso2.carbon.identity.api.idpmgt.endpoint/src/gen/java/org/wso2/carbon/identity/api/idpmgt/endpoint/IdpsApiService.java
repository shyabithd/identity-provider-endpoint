package org.wso2.carbon.identity.api.idpmgt.endpoint;

import org.wso2.carbon.identity.api.idpmgt.endpoint.*;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.*;

import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.ErrorDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.IdpsDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.IdentityProviderDTO;

import java.util.List;

import java.io.InputStream;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;

import javax.ws.rs.core.Response;

public abstract class IdpsApiService {
    public abstract Response idpsGet(String id,Integer limit,Integer offset);
    public abstract Response idpsIdDelete(String id);
    public abstract Response idpsIdGet(String id);
    public abstract Response idpsIdPut(String id,IdentityProviderDTO identityProvider);
    public abstract Response idpsPost(IdentityProviderDTO identityProvider);
}

