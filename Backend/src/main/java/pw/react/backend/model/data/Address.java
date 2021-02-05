package pw.react.backend.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "address")  // Generate table in database with fields below
@Data // Lombok - autogenerate all getters and setters for variables
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonPropertyOrder({"id","country","town","streetName","streetNumber"})
public class Address implements Serializable {

    private static final long serialVersionUID = -6783504532088859179L;

    public static Address EMPTY = new Address();

    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(position = 1)
    private long id;

    @Column(name = "country")
    @ApiModelProperty(position = 2)
    private String country;

    @Column(name = "town")
    @ApiModelProperty(position = 3)
    private String town;

    @Column(name = "streetName")
    @ApiModelProperty(position = 4)
    private String streetName;

    @Column(name = "streetNumber")
    @ApiModelProperty(position = 5)
    private String streetNumber;
}
