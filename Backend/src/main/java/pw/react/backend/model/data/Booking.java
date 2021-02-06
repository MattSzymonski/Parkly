package pw.react.backend.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import pw.react.backend.model.bookly.BooklyBooking;
import pw.react.backend.utils.JsonDateDeserializer;
import pw.react.backend.utils.JsonDateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;



@Entity
@Table(name = "booking")  // Generate table in database with fields below
@Data // Lombok - autogenerate all getters and setters for variables
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Booking implements Serializable {

    private static final long serialVersionUID = -6783504532088859179L;

    //public static Booking EMPTY = new Booking();
    //public static Booking EMPTY = new Booking();




    @Id    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(position = 1)
    private long id;

    @Column(name = "userId")
    @ApiModelProperty(position = 2)
    private long userId;

    @Column(name = "userFirstName")
    @ApiModelProperty(position = 3)
    private String userFirstName;

    @Column(name = "userLastName")
    @ApiModelProperty(position = 4)
    private String userLastName;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST) // Do not destroy address entry
    @JoinColumn(name = "parking_id", referencedColumnName = "id")
    @ApiModelProperty(position = 5)
    private Parking parking;

    @Column(name = "startDateTime")  
    @JsonDeserialize(using = JsonDateDeserializer.class) // For data decoding
    @JsonSerialize(using = JsonDateSerializer.class) // For data encoding
	@ApiModelProperty(position = 6)
    public LocalDateTime startDateTime;

    @Column(name = "endDateTime")
    @JsonDeserialize(using = JsonDateDeserializer.class) // For data decoding
    @JsonSerialize(using = JsonDateSerializer.class) // For data encoding
    @ApiModelProperty(position = 7)
    private LocalDateTime endDateTime;

    public Booking() {}

    public Booking(BooklyBooking booklyBooking, Parking parking) {
        this.userId = booklyBooking.getUserId();
        this.userFirstName = booklyBooking.getUserFirstName();
        this.userLastName = booklyBooking.getUserLastName();
        this.parking = parking;
        this.startDateTime= booklyBooking.getStartDateTime();
        this.endDateTime = booklyBooking.getEndDateTime();
     }

}
