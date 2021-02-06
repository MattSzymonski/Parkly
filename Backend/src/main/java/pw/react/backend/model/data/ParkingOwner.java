package pw.react.backend.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "parkingOwner")  // Generate table in database with fields below
@Data // Lombok - autogenerate all getters and setters for variables
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ParkingOwner implements Serializable {

    private static final long serialVersionUID = -6783504532088859179L;

    public static ParkingOwner EMPTY = new ParkingOwner();

    @Id    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(position = 1)
    private long id;

    @Column(name = "firstName")
    @ApiModelProperty(position = 2)
    private String firstName;

    @Column(name = "lastName")
    @ApiModelProperty(position = 3)
    private String lastName;

    @Column(name = "companyName")
    @ApiModelProperty(position = 4)
    private String companyName;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST) // Do not destroy address entry
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @ApiModelProperty(position = 5)
    private Address address;
}
