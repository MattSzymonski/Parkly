package pw.react.backend.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "parklyUser")  // Generate table in database with fields below
@Data // Lombok - autogenerate all getters and setters for variables
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ParklyUser implements Serializable {

    private static final long serialVersionUID = -6783504532088859179L;

    public static ParklyUser EMPTY = new ParklyUser();

    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(position = 1)
    private long id;

    @Column(name = "login")
    @ApiModelProperty(position = 2)
    private String login;

    @Column(name = "password")
    @ApiModelProperty(position = 3)
    private String password;

    @Column(name = "firstName")
    @ApiModelProperty(position = 4)
    private String firstName;

    @Column(name = "lastName")
    @ApiModelProperty(position = 5)
    private String lastName;

    @Column(name = "securityToken")
    @ApiModelProperty(position = 6)
    private String securityToken;
}
