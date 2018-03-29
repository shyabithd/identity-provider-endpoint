package org.wso2.carbon.identity.provider.mgt.endpoint.dto;

import io.swagger.annotations.ApiModel;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;



/**
 * Claim mapping
 **/


@ApiModel(description = "Claim mapping")
public class ClaimMappingDTO  {
  
  
  
  private String localClaim = null;
  
  
  private String remoteClaim = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("localClaim")
  public String getLocalClaim() {
    return localClaim;
  }
  public void setLocalClaim(String localClaim) {
    this.localClaim = localClaim;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("remoteClaim")
  public String getRemoteClaim() {
    return remoteClaim;
  }
  public void setRemoteClaim(String remoteClaim) {
    this.remoteClaim = remoteClaim;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClaimMappingDTO {\n");
    
    sb.append("  localClaim: ").append(localClaim).append("\n");
    sb.append("  remoteClaim: ").append(remoteClaim).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
