package org.wso2.carbon.identity.api.idpmgt.endpoint;

import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.*;
import org.wso2.carbon.identity.api.idpmgt.endpoint.IdpsApiService;
import org.wso2.carbon.identity.api.idpmgt.endpoint.factories.IdpsApiServiceFactory;

import io.swagger.annotations.ApiParam;

import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.IdPListDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.ErrorDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.IdPDetailDTO;

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
        
        @io.swagger.annotations.ApiResponse(code = 304, message = "Not Modified.\nEmpty body because the client has already the latest version of the requested resource (Will be supported in future).\n"),
        
        @io.swagger.annotations.ApiResponse(code = 406, message = "Not Acceptable.\nThe requested media type is not supported\n") })

    public Response idpsGet(@ApiParam(value = "Maximum size of resource array to return.\n", defaultValue="25") @QueryParam("limit")  Integer limit,
    @ApiParam(value = "Starting point within the complete list of items qualified.\n", defaultValue="0") @QueryParam("offset")  Integer offset,
    @ApiParam(value = "description") @QueryParam("query")  String query,
    @ApiParam(value = "Media types acceptable for the response. Default is application/json.\n"  , defaultValue="application/json")@HeaderParam("Accept") String accept,
    @ApiParam(value = "Validator for conditional requests; based on the ETag of the formerly retrieved\nvariant of the resource (Will be supported in future).\n"  )@HeaderParam("If-None-Match") String ifNoneMatch,
    @ApiParam(value = "Defines whether the returned response should contain full details of API\n") @QueryParam("expand")  Boolean expand)
    {
    return delegate.idpsGet(limit,offset,query,accept,ifNoneMatch,expand);
    }
    @DELETE
    @Path("/{id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json", "application/gzip" })
    @io.swagger.annotations.ApiOperation(value = "Delete specified IdP from the IS\n", notes = "This operation deletes an existing IdP\n", response = IdPDetailDTO.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK.\nList of IdP returned.\n"),
        
        @io.swagger.annotations.ApiResponse(code = 304, message = "Not Modified.\nEmpty body because the client has already the latest version of the requested resource (Will be supported in future).\n"),
        
        @io.swagger.annotations.ApiResponse(code = 406, message = "Not Acceptable.\nThe requested media type is not supported\n") })

    public Response idpsIdDelete(@ApiParam(value = "The unique identifier of a receipt",required=true ) @PathParam("id")  String id,
    @ApiParam(value = "Media types acceptable for the response. Default is application/json.\n"  , defaultValue="application/json")@HeaderParam("Accept") String accept,
    @ApiParam(value = "Validator for conditional requests; based on the ETag of the formerly retrieved\nvariant of the resource (Will be supported in future).\n"  )@HeaderParam("If-None-Match") String ifNoneMatch)
    {
    return delegate.idpsIdDelete(id,accept,ifNoneMatch);
    }
    @GET
    @Path("/{id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json", "application/gzip" })
    @io.swagger.annotations.ApiOperation(value = "Retrieve/Search IdPs\n", notes = "This operation provides you a list of available IdPs\n", response = IdPDetailDTO.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK.\nList of IdP returned.\n"),
        
        @io.swagger.annotations.ApiResponse(code = 304, message = "Not Modified.\nEmpty body because the client has already the latest version of the requested resource (Will be supported in future).\n"),
        
        @io.swagger.annotations.ApiResponse(code = 406, message = "Not Acceptable.\nThe requested media type is not supported\n") })

    public Response idpsIdGet(@ApiParam(value = "The unique identifier of a receipt",required=true ) @PathParam("id")  String id,
    @ApiParam(value = "Media types acceptable for the response. Default is application/json.\n"  , defaultValue="application/json")@HeaderParam("Accept") String accept,
    @ApiParam(value = "Validator for conditional requests; based on the ETag of the formerly retrieved\nvariant of the resource (Will be supported in future).\n"  )@HeaderParam("If-None-Match") String ifNoneMatch)
    {
    return delegate.idpsIdGet(id,accept,ifNoneMatch);
    }
    @PUT
    @Path("/{id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json", "application/gzip" })
    @io.swagger.annotations.ApiOperation(value = "update an existing IdP\n", notes = "This operation updates an existing IdP\n", response = IdPDetailDTO.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "OK.\nupdated IdP successfully.\n"),
        
        @io.swagger.annotations.ApiResponse(code = 304, message = "Not Modified.\nEmpty body because the client has already the latest version of the requested resource (Will be supported in future).\n"),
        
        @io.swagger.annotations.ApiResponse(code = 406, message = "Not Acceptable.\nThe requested media type is not supported\n") })

    public Response idpsIdPut(@ApiParam(value = "The unique identifier of a receipt",required=true ) @PathParam("id")  String id,
    @ApiParam(value = "IdP object that needs to be updated\n" ,required=true ) IdPDetailDTO body,
    @ApiParam(value = "Media type of the entity in the body. Default is application/json.\n" ,required=true , defaultValue="application/json")@HeaderParam("Content-Type") String contentType)
    {
    return delegate.idpsIdPut(id,body,contentType);
    }
    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Create a new IdP", notes = "This operation can be used to create a new IdP specifying the details of the API in the payload. The new API will be in `CREATED` state.\n", response = IdPDetailDTO.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 201, message = "Created.\nSuccessful response with the newly created object as entity in the body.\nLocation header contains URL of newly created entity.\n"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request.\nInvalid request or validation error.\n"),
        
        @io.swagger.annotations.ApiResponse(code = 415, message = "Unsupported Media Type.\nThe entity of the request was in a not supported format.\n") })

    public Response idpsPost(@ApiParam(value = "IdP object that needs to be added\n" ,required=true ) IdPDetailDTO body,
    @ApiParam(value = "Media type of the entity in the body. Default is application/json.\n" ,required=true , defaultValue="application/json")@HeaderParam("Content-Type") String contentType)
    {
    return delegate.idpsPost(body,contentType);
    }
}

