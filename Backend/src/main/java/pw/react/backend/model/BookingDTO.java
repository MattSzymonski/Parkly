package pw.react.backend.model;

import lombok.Getter;
import lombok.Setter;
import pw.react.backend.model.data.Booking;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;

public class BookingDTO { // This object represents one entry in boking list. It is send to Parkly frontend and Bookly backend
    
    @ApiModelProperty(position = 1)
    @Getter @Setter private long id; // Booking id

    @ApiModelProperty(position = 2)
    @Getter @Setter private long userId; // Booking id

    @ApiModelProperty(position = 3)
    @Getter @Setter private String userFirstName;

    @ApiModelProperty(position = 4)
    @Getter @Setter private String userLastName;

    @ApiModelProperty(position = 5)
    @Getter @Setter private Float totalPrice;

    @ApiModelProperty(position = 6)
    @Getter @Setter private long parkingId;

    @ApiModelProperty(position = 7)
    @Getter @Setter private String parkingName;

    @ApiModelProperty(position = 8)
    @Getter @Setter private LocalDateTime startDateTime;

    @ApiModelProperty(position = 9)
    @Getter @Setter private LocalDateTime endDateTime;

    public BookingDTO(Booking booking) { 
        this.id = booking.getId();
        this.userId = booking.getUserId();
        this.userFirstName = booking.getUserFirstName();
        this.userLastName = booking.getUserLastName();
        this.totalPrice = booking.getTotalPrice();
        this.parkingId = booking.getParking().getId();
        this.parkingName = booking.getParking().getName();
        this.startDateTime= booking.getStartDateTime();
        this.endDateTime = booking.getEndDateTime();
    }
}
