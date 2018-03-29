package org.wso2.carbon.identity.provider.mgt.endpoint;

import org.wso2.carbon.identity.provider.mgt.endpoint.dto.*;
import org.wso2.carbon.identity.provider.mgt.endpoint.IdpsApiService;
import org.wso2.carbon.identity.provider.mgt.endpoint.factories.IdpsApiServiceFactory;

import io.swagger.annotations.ApiParam;

import org.wso2.carbon.identity.provider.mgt.endpoint.dto.ErrorDTO;
import org.wso2.carbon.identity.provider.mgt.endpoint.dto.IdpsDTO;
import org.wso2.carbon.identity.provider.mgt.endpoint.dto.IdentityProviderDTO;

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
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "List Identity Providers.\n", notes = "This API is used to list Identity Providers based on the filtered attributes.\n", response = IdpsDTO.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response idpsGet(@ApiParam(value = "The unique identifier of a receipt",required=true ) @PathParam("id")  String id,
    @ApiParam(value = "Number of search results") @QueryParam("limit")  Integer limit,
    @ApiParam(value = "Start index of the search") @QueryParam("offset")  Integer offset)
    {
    return delegate.idpsGet(id,limit,offset);
    }
    @DELETE
    @Path("/{id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Deletes an IDP\n", notes = "This API is used to delete an IDP.\n", response = void.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response idpsIdDelete(@ApiParam(value = "The unique identifier of a receipt",required=true ) @PathParam("id")  String id)
    {
    return delegate.idpsIdDelete(id);
    }
    @GET
    @Path("/{id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Retrieves an Identity Provider.\n", notes = "This API is used to retrieve an Identity Provider using the IDP ID.\n", response = IdentityProviderDTO.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response idpsIdGet(@ApiParam(value = "The unique identifier of a receipt",required=true ) @PathParam("id")  String id)
    {
    return delegate.idpsIdGet(id);
    }
    @PUT
    @Path("/{id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Updates an IDP\n", notes = "This API is used to update an IDP.\n", response = IdentityProviderDTO.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response idpsIdPut(@ApiParam(value = "The unique identifier of a receipt",required=true ) @PathParam("id")  String id,
    @ApiParam(value = "This represents the Identity Provider element that needs to be stored." ,required=true ) IdentityProviderDTO identityProvider)
    {
    return delegate.idpsIdPut(id,identityProvider);
    }
    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Adds Identity Provider\n", notes = "This API is used to store Identity Provider information.\n", response = IdentityProviderDTO.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 201, message = "Successful response"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 409, message = "Conflict"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response idpsPost(@ApiParam(value = "This represents the Identity Provider element that needs to be stored." ,required=true ) IdentityProviderDTO identityProvider)
    {
    return delegate.idpsPost(identityProvider);
    }
}

