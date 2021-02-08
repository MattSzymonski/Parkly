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

    @Column(name = "price")
    @ApiModelProperty(position = 5)
    private Float price;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST) // Do not destroy address entry
    @JoinColumn(name = "parking_id", referencedColumnName = "id")
    @ApiModelProperty(position = 6)
    private Parking parking;

    @Column(name = "startDateTime")  
    @JsonDeserialize(using = JsonDateDeserializer.class) // For data decoding
    @JsonSerialize(using = JsonDateSerializer.class) // For data encoding
	@ApiModelProperty(position = 7)
    public LocalDateTime startDateTime;

    @Column(name = "endDateTime")
    @JsonDeserialize(using = JsonDateDeserializer.class) // For data decoding
    @JsonSerialize(using = JsonDateSerializer.class) // For data encoding
    @ApiModelProperty(position = 8)
    private LocalDateTime endDateTime;

    public Booking() {}

    public Booking(BooklyBooking booklyBooking, Parking parking, long hoursCount) {
        this.userId = booklyBooking.getUserId();
        this.userFirstName = booklyBooking.getUserFirstName();
        this.userLastName = booklyBooking.getUserLastName();
        this.price = parking.getPricePerHour() * hoursCount;
        this.parking = parking;
        this.startDateTime= booklyBooking.getStartDateTime();
        this.endDateTime = booklyBooking.getEndDateTime();
     }

}
