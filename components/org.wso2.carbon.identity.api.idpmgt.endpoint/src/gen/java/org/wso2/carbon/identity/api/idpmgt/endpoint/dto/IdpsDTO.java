package org.wso2.carbon.identity.api.idpmgt.endpoint.dto;

import java.util.ArrayList;
import java.util.List;
import org.wso2.carbon.identity.api.idpmgt.endpoint.dto.IDPListResponseDTO;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;





@ApiModel(description = "")
public class IdpsDTO extends ArrayList<IDPListResponseDTO> {
  

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class IdpsDTO {\n");
    sb.append("  " + super.toString()).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
