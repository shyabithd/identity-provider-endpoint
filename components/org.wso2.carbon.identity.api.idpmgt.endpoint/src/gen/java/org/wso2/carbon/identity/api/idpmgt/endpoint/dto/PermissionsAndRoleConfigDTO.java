package org.wso2.carbon.identity.api.idpmgt.endpoint.dto;

import java.util.ArrayList;
import java.util.List;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.ApplicationPermissionDTO;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.RoleMappingDTO;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;





@ApiModel(description = "")
public class PermissionsAndRoleConfigDTO  {
  
  
  
  private String idpRoles = null;
  
  
  private List<ApplicationPermissionDTO> permissions = new ArrayList<ApplicationPermissionDTO>();
  
  
  private List<RoleMappingDTO> roleMappings = new ArrayList<RoleMappingDTO>();

  
  /**
   * idpRoles
   **/
  @ApiModelProperty(value = "idpRoles")
  @JsonProperty("idpRoles")
  public String getIdpRoles() {
    return idpRoles;
  }
  public void setIdpRoles(String idpRoles) {
    this.idpRoles = idpRoles;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("permissions")
  public List<ApplicationPermissionDTO> getPermissions() {
    return permissions;
  }
  public void setPermissions(List<ApplicationPermissionDTO> permissions) {
    this.permissions = permissions;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("roleMappings")
  public List<RoleMappingDTO> getRoleMappings() {
    return roleMappings;
  }
  public void setRoleMappings(List<RoleMappingDTO> roleMappings) {
    this.roleMappings = roleMappings;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class PermissionsAndRoleConfigDTO {\n");
    
    sb.append("  idpRoles: ").append(idpRoles).append("\n");
    sb.append("  permissions: ").append(permissions).append("\n");
    sb.append("  roleMappings: ").append(roleMappings).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
