package pw.react.backend.model;

import lombok.Getter;
import lombok.Setter;
import pw.react.backend.model.bookly.BooklyUser;
import pw.react.backend.model.data.Address;
import pw.react.backend.model.data.Booking;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;

public class BookingDetailDTO { // This object represents one entry in boking list. It is send to Parkly frontend and Bookly backend
    
    public static BookingDetailDTO EMPTY = new BookingDetailDTO();

    @ApiModelProperty(position = 1)
    @Getter @Setter private long id; // Booking id

    @ApiModelProperty(position = 2)
    @Getter @Setter private long userId;

    @ApiModelProperty(position = 3)
    @Getter @Setter private String userFirstName;

    @ApiModelProperty(position = 4)
    @Getter @Setter private String userLastName;

    @ApiModelProperty(position = 5)
    @Getter @Setter private String userPhoneNumber;

    @ApiModelProperty(position = 6)
    @Getter @Setter private String userEmailAddress;

    @ApiModelProperty(position = 7)
    @Getter @Setter private String userRegistrationPlates;

    @ApiModelProperty(position = 8)
    @Getter @Setter private long parkingId;

    @ApiModelProperty(position = 9)
    @Getter @Setter private String parkingName;

    @ApiModelProperty(position = 10)
    @Getter @Setter private String parkingOwnerCompanyName;

    @ApiModelProperty(position = 11)
    @Getter @Setter private Address parkingAddress;

    @ApiModelProperty(position = 12)
    @Getter @Setter private LocalDateTime startDateTime;

    @ApiModelProperty(position = 13)
    @Getter @Setter private LocalDateTime endDateTime;

    public BookingDetailDTO(Booking booking, BooklyUser booklyUser) {
        this.id = booking.getId();
        this.userId = booking.getUserId();
        this.userFirstName = booking.getUserFirstName();
        this.userLastName = booking.getUserLastName();
        this.userPhoneNumber = booklyUser.getPhoneNumber();
        this.userEmailAddress = booklyUser.getEmailAddress();
        this.userRegistrationPlates = booklyUser.getRegistrationPlates();
        this.parkingId = booking.getParking().getId();
        this.parkingName = booking.getParking().getName();
        this.parkingOwnerCompanyName = booking.getParking().getParkingOwner().getCompanyName();
        this.parkingAddress = booking.getParking().getAddress();
        this.startDateTime= booking.getStartDateTime();
        this.endDateTime = booking.getEndDateTime();
    }

    public BookingDetailDTO() {
    }
}
