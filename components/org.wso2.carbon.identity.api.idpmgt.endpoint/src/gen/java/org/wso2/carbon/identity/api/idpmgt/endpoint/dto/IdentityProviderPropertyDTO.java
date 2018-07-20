package org.wso2.carbon.identity.api.idpmgt.endpoint.dto;


import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;





@ApiModel(description = "")
public class IdentityProviderPropertyDTO  {
  
  
  
  private String displayName = null;
  
  
  private String name = null;
  
  
  private String value = null;

  
  /**
   * displayName
   **/
  @ApiModelProperty(value = "displayName")
  @JsonProperty("displayName")
  public String getDisplayName() {
    return displayName;
  }
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  
  /**
   * name
   **/
  @ApiModelProperty(value = "name")
  @JsonProperty("name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  
  /**
   * value
   **/
  @ApiModelProperty(value = "value")
  @JsonProperty("value")
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class IdentityProviderPropertyDTO {\n");
    
    sb.append("  displayName: ").append(displayName).append("\n");
    sb.append("  name: ").append(name).append("\n");
    sb.append("  value: ").append(value).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
