package pw.react.backend.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public class Credentials { 
    
    @ApiModelProperty(position = 1)
    @Getter @Setter private String login;
    
    @ApiModelProperty(position = 2)
    @Getter @Setter private String password;

}
