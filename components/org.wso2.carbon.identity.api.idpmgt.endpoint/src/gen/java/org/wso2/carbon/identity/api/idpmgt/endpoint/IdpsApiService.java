package org.wso2.carbon.identity.api.idpmgt.endpoint;

import org.wso2.carbon.identity.api.idpmgt.endpoint.*;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.*;

import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.IdPListDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.ErrorDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.FederatedAuthenticatorConfigDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.ClaimConfigDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.IdPDetailDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.JustInTimeProvisioningConfigDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.ProvisioningConnectorConfigDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.PermissionsAndRoleConfigDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.IdPAddResponseDTO;

import java.util.List;

import java.io.InputStream;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;

import javax.ws.rs.core.Response;

public abstract class IdpsApiService {
    public abstract Response idpsGet(Integer limit,Integer offset,String spTenantDomain);
    public abstract Response idpsIdAuthenticatorsGet(String id,Integer limit,Integer offset);
    public abstract Response idpsIdAuthenticatorsPut(String id,FederatedAuthenticatorConfigDTO body);
    public abstract Response idpsIdClaimsGet(String id);
    public abstract Response idpsIdClaimsPut(String id,ClaimConfigDTO body);
    public abstract Response idpsIdDelete(String id);
    public abstract Response idpsIdGet(String id);
    public abstract Response idpsIdJitProvisioningGet(String id);
    public abstract Response idpsIdJitProvisioningPut(String id,JustInTimeProvisioningConfigDTO body);
    public abstract Response idpsIdOutboundConnectorsGet(String id,Integer limit,Integer offset);
    public abstract Response idpsIdOutboundConnectorsPut(String id,ProvisioningConnectorConfigDTO body);
    public abstract Response idpsIdPut(String id,IdPDetailDTO body);
    public abstract Response idpsIdRolesGet(String id);
    public abstract Response idpsIdRolesPut(String id,PermissionsAndRoleConfigDTO body);
    public abstract Response idpsPost(IdPDetailDTO body);
}

