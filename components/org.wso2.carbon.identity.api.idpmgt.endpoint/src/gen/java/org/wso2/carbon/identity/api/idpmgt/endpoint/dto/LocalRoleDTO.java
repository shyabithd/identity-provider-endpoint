package org.wso2.carbon.identity.api.idpmgt.endpoint.dto;


import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;





@ApiModel(description = "")
public class LocalRoleDTO  {
  
  
  
  private String localRoleName = null;
  
  
  private String userStoreId = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("localRoleName")
  public String getLocalRoleName() {
    return localRoleName;
  }
  public void setLocalRoleName(String localRoleName) {
    this.localRoleName = localRoleName;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("userStoreId")
  public String getUserStoreId() {
    return userStoreId;
  }
  public void setUserStoreId(String userStoreId) {
    this.userStoreId = userStoreId;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class LocalRoleDTO {\n");
    
    sb.append("  localRoleName: ").append(localRoleName).append("\n");
    sb.append("  userStoreId: ").append(userStoreId).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
