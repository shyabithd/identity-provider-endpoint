package org.wso2.carbon.identity.api.idpmgt.endpoint.dto;


import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;





@ApiModel(description = "")
public class RoleMappingDTO  {
  
  
  
  private Object localRole = null;
  
  
  private String remoteRole = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("localRole")
  public Object getLocalRole() {
    return localRole;
  }
  public void setLocalRole(Object localRole) {
    this.localRole = localRole;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("remoteRole")
  public String getRemoteRole() {
    return remoteRole;
  }
  public void setRemoteRole(String remoteRole) {
    this.remoteRole = remoteRole;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class RoleMappingDTO {\n");
    
    sb.append("  localRole: ").append(localRole).append("\n");
    sb.append("  remoteRole: ").append(remoteRole).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
