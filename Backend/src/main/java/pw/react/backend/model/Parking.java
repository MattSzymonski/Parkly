package pw.react.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
    private long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Column(name = "spotsTotal")
    private int spotsTotal;

    @Column(name = "spotsTaken")
    private int spotsTaken;

    @Column(name = "ownerId")
    private long ownerId;

    @Column(name = "addedDateTime")
    @JsonDeserialize(using = JsonDateDeserializer.class) // For data decoding
    @JsonSerialize(using = JsonDateSerializer.class) // For data encoding
    private LocalDateTime addedDateTime;
}
