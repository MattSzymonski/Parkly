package pw.react.backend.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import pw.react.backend.utils.JsonDateDeserializer;
import pw.react.backend.utils.JsonDateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking")  // Generate table in database with fields below
@Data // Lombok - autogenerate all getters and setters for variables
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Parking implements Serializable {

    private static final long serialVersionUID = -6783504532088859179L;

    public static Parking EMPTY = new Parking();

    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(position = 1)
    private long id;

    @Column(name = "name")
    @ApiModelProperty(position = 2)
    private String name;

    @Column(name = "spotsTotal")
    @ApiModelProperty(position = 3)
    private int spotsTotal; 

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST) // Do not destroy address entry
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @ApiModelProperty(position = 4)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST) // Do not destroy address entry
    @JoinColumn(name = "parkingOwner_id", referencedColumnName = "id")
    @ApiModelProperty(position = 5)
    private ParkingOwner parkingOwner;

    @Column(name = "addedDateTime")
    @JsonDeserialize(using = JsonDateDeserializer.class) // For data decoding
    @JsonSerialize(using = JsonDateSerializer.class) // For data encoding
    @CreationTimestamp
    @ApiModelProperty(position = 6)
    private LocalDateTime addedDateTime;
}
