package org.wso2.carbon.identity.api.idpmgt.endpoint;

import org.wso2.carbon.identity.api.idpmgt.endpoint.*;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.*;

import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.IdPListDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.ErrorDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.IdPDetailDTO;

import java.util.List;

import java.io.InputStream;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;

import javax.ws.rs.core.Response;

public abstract class IdpsApiService {
    public abstract Response idpsGet(Integer limit,Integer offset,String query,String accept,String ifNoneMatch,Boolean expand);
    public abstract Response idpsIdDelete(String id,String accept,String ifNoneMatch);
    public abstract Response idpsIdGet(String id,String accept,String ifNoneMatch);
    public abstract Response idpsIdPut(String id,IdPDetailDTO body,String contentType);
    public abstract Response idpsPost(IdPDetailDTO body,String contentType);
}

