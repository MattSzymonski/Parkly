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
import java.util.Optional;


@Entity
@Table(name = "booking")  // Generate table in database with fields below
@Data // Lombok - autogenerate all getters and setters for variables
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Booking implements Serializable {

    private static final long serialVersionUID = -6783504532088859179L;

    //public static Booking EMPTY = new Booking();
    //public static Booking EMPTY = new Booking();




    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "booklyUserId")
    private long booklyUserId;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST) // Do not destroy address entry
    @JoinColumn(name = "parking_id", referencedColumnName = "id")
    private Parking parking;

    @Column(name = "startDateTime")
    @JsonDeserialize(using = JsonDateDeserializer.class) // For data decoding
    @JsonSerialize(using = JsonDateSerializer.class) // For data encoding
    private LocalDateTime startDateTime;

    @Column(name = "endDateTime")
    @JsonDeserialize(using = JsonDateDeserializer.class) // For data decoding
    @JsonSerialize(using = JsonDateSerializer.class) // For data encoding
    private LocalDateTime endDateTime;



    // public Long getBooklyUserId() {
	// 	return id;
    // }

    // public Long getParkingId() {
	// 	return parking.getId();
    // }
    
    // public LocalDateTime getStartDateTime() {
	// 	return startDateTime;
    // }

    // public LocalDateTime getEndDateTime() {
	// 	return endDateTime;
    // }

    public Booking(long booklyBookingId, Optional<Parking> parking,  LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.booklyUserId = booklyBookingId;
        this.parking = parking.get();
        this.startDateTime= startDateTime;
        this.endDateTime = endDateTime;
    }
   public Booking() {

    }
}
