package org.wso2.carbon.identity.api.idpmgt.endpoint;

import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.*;
import org.wso2.carbon.identity.api.idpmgt.endpoint.IdpsApiService;
import org.wso2.carbon.identity.api.idpmgt.endpoint.factories.IdpsApiServiceFactory;

import io.swagger.annotations.ApiParam;

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
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import javax.ws.rs.core.Response;
import javax.ws.rs.*;

@Path("/idps")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@io.swagger.annotations.Api(value = "/idps", description = "the idps API")
public class IdpsApi  {

   private final IdpsApiService delegate = IdpsApiServiceFactory.getIdpsApi();

    @GET
    
    @Consumes({ "application/json" })
    @Produces({ "application/json", "application/gzip" })
    @io.swagger.annotations.ApiOperation(value = "List all IdPs\n", notes = "This operation provides you a list of available IdPs\n", response = IdPListDTO.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK.\nList of IdP returned.\n"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response idpsGet(@ApiParam(value = "Maximum size of resource array to return.\n", defaultValue="25") @QueryParam("limit")  Integer limit,
    @ApiParam(value = "Starting point within the complete list of items qualified.\n", defaultValue="0") @QueryParam("offset")  Integer offset,
    @ApiParam(value = "Service provider tenant domain") @QueryParam("spTenantDomain")  String spTenantDomain)
    {
    return delegate.idpsGet(limit,offset,spTenantDomain);
    }
    @GET
    @Path("/{id}/authenticators/")
    @Consumes({ "application/json" })
    @Produces({ "application/json", "application/gzip" })
    @io.swagger.annotations.ApiOperation(value = "listing authenticators for a given identity provider\n", notes = "This operation lists authenticators for an identity provider\n", response = FederatedAuthenticatorConfigDTO.class, responseContainer = "List")
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK.\nList of authenticators for the IdP returned.\n"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response idpsIdAuthenticatorsGet(@ApiParam(value = "The unique identifier of a receipt",required=true ) @PathParam("id")  String id)
    {
    return delegate.idpsIdAuthenticatorsGet(id);
    }
    @PUT
    @Path("/{id}/authenticators/")
    @Consumes({ "application/json" })
    @Produces({ "application/json", "application/gzip" })
    @io.swagger.annotations.ApiOperation(value = "update an authenticator in a given identity provider\n", notes = "This operation updates an authenticator for a given identity provider\n", response = void.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK. updated Authenticator successfully.\n"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response idpsIdAuthenticatorsPut(@ApiParam(value = "The unique identifier of a receipt",required=true ) @PathParam("id")  String id,
    @ApiParam(value = "authenticator object that needs to be updated\n" ,required=true ) FederatedAuthenticatorConfigDTO body)
    {
    return delegate.idpsIdAuthenticatorsPut(id,body);
    }
    @GET
    @Path("/{id}/claims/")
    @Consumes({ "application/json" })
    @Produces({ "application/json", "application/gzip" })
    @io.swagger.annotations.ApiOperation(value = "list claim configuration for a given identity provider\n", notes = "This operation lists claim configuration for a given identity provider\n", response = ClaimConfigDTO.class, responseContainer = "List")
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK.\nList of claim configurations for the IdP returned.\n"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response idpsIdClaimsGet(@ApiParam(value = "The unique identifier of a receipt",required=true ) @PathParam("id")  String id)
    {
    return delegate.idpsIdClaimsGet(id);
    }
    @PUT
    @Path("/{id}/claims/")
    @Consumes({ "application/json" })
    @Produces({ "application/json", "application/gzip" })
    @io.swagger.annotations.ApiOperation(value = "update an existing claim configuration in a given identity provider\n", notes = "This operation updates an existing claim configuration in a given identity provider\n", response = void.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK. updated Authenticator successfully.\n"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response idpsIdClaimsPut(@ApiParam(value = "The unique identifier of a receipt",required=true ) @PathParam("id")  String id,
    @ApiParam(value = "claim object that needs to be updated\n" ,required=true ) ClaimConfigDTO body)
    {
    return delegate.idpsIdClaimsPut(id,body);
    }
    @DELETE
    @Path("/{id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json", "application/gzip" })
    @io.swagger.annotations.ApiOperation(value = "Delete specified IdP from the IS\n", notes = "This operation deletes an existing IdP\n", response = void.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response idpsIdDelete(@ApiParam(value = "The unique identifier of a receipt",required=true ) @PathParam("id")  String id)
    {
    return delegate.idpsIdDelete(id);
    }
    @GET
    @Path("/{id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json", "application/gzip" })
    @io.swagger.annotations.ApiOperation(value = "Retrieve/Search IdPs\n", notes = "This operation provides you a list of available IdPs\n", response = IdPDetailDTO.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK.\nList of IdP returned.\n"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response idpsIdGet(@ApiParam(value = "The unique identifier of a receipt",required=true ) @PathParam("id")  String id)
    {
    return delegate.idpsIdGet(id);
    }
    @GET
    @Path("/{id}/jit-provisioning/")
    @Consumes({ "application/json" })
    @Produces({ "application/json", "application/gzip" })
    @io.swagger.annotations.ApiOperation(value = "list jit provisioning for a given identity provider\n", notes = "This operation lists jit provisioning for a given identity provider\n", response = JustInTimeProvisioningConfigDTO.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK.\nList of jit provisioning for the IdP returned.\n"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response idpsIdJitProvisioningGet(@ApiParam(value = "The unique identifier of a receipt",required=true ) @PathParam("id")  String id)
    {
    return delegate.idpsIdJitProvisioningGet(id);
    }
    @PUT
    @Path("/{id}/jit-provisioning/")
    @Consumes({ "application/json" })
    @Produces({ "application/json", "application/gzip" })
    @io.swagger.annotations.ApiOperation(value = "update jit provisioning in a given identity provider\n", notes = "This operation updates jit provisioning in a given identity provider\n", response = void.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK. updated Role successfully.\n"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response idpsIdJitProvisioningPut(@ApiParam(value = "The unique identifier of a receipt",required=true ) @PathParam("id")  String id,
    @ApiParam(value = "role object that needs to be updated\n" ,required=true ) JustInTimeProvisioningConfigDTO body)
    {
    return delegate.idpsIdJitProvisioningPut(id,body);
    }
    @GET
    @Path("/{id}/outbound-connectors/")
    @Consumes({ "application/json" })
    @Produces({ "application/json", "application/gzip" })
    @io.swagger.annotations.ApiOperation(value = "list out bound connectors for a given identity provider\n", notes = "This operation lists outbound connectors for a given identity provider\n", response = ProvisioningConnectorConfigDTO.class, responseContainer = "List")
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK.\nList of outbound connectors for the IdP returned.\n"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response idpsIdOutboundConnectorsGet(@ApiParam(value = "The unique identifier of a receipt",required=true ) @PathParam("id")  String id)
    {
    return delegate.idpsIdOutboundConnectorsGet(id);
    }
    @PUT
    @Path("/{id}/outbound-connectors/")
    @Consumes({ "application/json" })
    @Produces({ "application/json", "application/gzip" })
    @io.swagger.annotations.ApiOperation(value = "update outbound connector in a given identity provider\n", notes = "This operation updates outbound connector for a given identity provider\n", response = void.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK. updated Role successfully.\n"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response idpsIdOutboundConnectorsPut(@ApiParam(value = "The unique identifier of a receipt",required=true ) @PathParam("id")  String id,
    @ApiParam(value = "outbound connector that needs to be updated\n" ,required=true ) ProvisioningConnectorConfigDTO body)
    {
    return delegate.idpsIdOutboundConnectorsPut(id,body);
    }
    @PUT
    @Path("/{id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json", "application/gzip" })
    @io.swagger.annotations.ApiOperation(value = "update an existing IdP\n", notes = "This operation updates an existing IdP\n", response = void.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK. updated IdP successfully.\n"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response idpsIdPut(@ApiParam(value = "The unique identifier of a receipt",required=true ) @PathParam("id")  String id,
    @ApiParam(value = "IdP object that needs to be updated\n" ,required=true ) IdPDetailDTO body)
    {
    return delegate.idpsIdPut(id,body);
    }
    @GET
    @Path("/{id}/roles/")
    @Consumes({ "application/json" })
    @Produces({ "application/json", "application/gzip" })
    @io.swagger.annotations.ApiOperation(value = "list roles for a given identity provider\n", notes = "This operation lists roles for a given identity provider\n", response = PermissionsAndRoleConfigDTO.class, responseContainer = "List")
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK.\nList of roles for the IdP returned.\n"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response idpsIdRolesGet(@ApiParam(value = "The unique identifier of a receipt",required=true ) @PathParam("id")  String id)
    {
    return delegate.idpsIdRolesGet(id);
    }
    @PUT
    @Path("/{id}/roles/")
    @Consumes({ "application/json" })
    @Produces({ "application/json", "application/gzip" })
    @io.swagger.annotations.ApiOperation(value = "update role configuration in a given identity provider\n", notes = "This operation updates role configuration in a given identity provider\n", response = void.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK. updated Role successfully.\n"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response idpsIdRolesPut(@ApiParam(value = "The unique identifier of a receipt",required=true ) @PathParam("id")  String id,
    @ApiParam(value = "role object that needs to be updated\n" ,required=true ) PermissionsAndRoleConfigDTO body)
    {
    return delegate.idpsIdRolesPut(id,body);
    }
    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Create a new IdP", notes = "This operation can be used to create a new IdP specifying the details of the API in the payload.\n", response = IdPAddResponseDTO.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 201, message = "Created.\nSuccessful response with the newly created object as entity in the body.\nLocation header contains URL of newly created entity.\n"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request.\nInvalid request or validation error.\n"),
        
        @io.swagger.annotations.ApiResponse(code = 415, message = "Unsupported Media Type.\nThe entity of the request was in a not supported format.\n") })

    public Response idpsPost(@ApiParam(value = "IdP object that needs to be added\n" ,required=true ) IdPDetailDTO body)
    {
    return delegate.idpsPost(body);
    }
}

