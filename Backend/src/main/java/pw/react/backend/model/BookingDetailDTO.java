package pw.react.backend.model;

import lombok.Getter;
import lombok.Setter;
import pw.react.backend.model.bookly.BooklyUser;
import pw.react.backend.model.data.Address;
import pw.react.backend.model.data.Booking;
import java.time.LocalDateTime;

public class BookingDetailDTO { // This object represents one entry in boking list. It is send to Parkly frontend and Bookly backend
    
    public static BookingDetailDTO EMPTY = new BookingDetailDTO();

    @Getter @Setter private long id; // Booking id
    @Getter @Setter private long userId;
    @Getter @Setter private String userFirstName;
    @Getter @Setter private String userLastName;
    @Getter @Setter private String userPhoneNumber;
    @Getter @Setter private String userEmailAddress;
    @Getter @Setter private String userRegistrationPlates;
    @Getter @Setter private long parkingId;
    @Getter @Setter private String parkingName;
    @Getter @Setter private String parkingOwnerCompanyName;
    @Getter @Setter private Address parkingAddress;
    @Getter @Setter private LocalDateTime startDateTime;
    @Getter @Setter private LocalDateTime endDateTime;

    public BookingDetailDTO() { }

    public static BookingDetailDTO createBookingDetailDTO(Booking booking, BooklyUser booklyUser) {
        BookingDetailDTO bookingDetailDTO = new BookingDetailDTO();

        bookingDetailDTO.id = booking.getId();
        bookingDetailDTO.userId = booking.getUserId();
        bookingDetailDTO.userFirstName = booking.getUserFirstName();
        bookingDetailDTO.userLastName = booking.getUserLastName();
        bookingDetailDTO.userPhoneNumber = booklyUser.getPhoneNumber();
        bookingDetailDTO.userEmailAddress = booklyUser.getEmailAddress();
        bookingDetailDTO.userRegistrationPlates = booklyUser.getRegistrationPlates();
        bookingDetailDTO.parkingId = booking.getParking().getId();
        bookingDetailDTO.parkingName = booking.getParking().getName();
        bookingDetailDTO.parkingOwnerCompanyName = booking.getParking().getParkingOwner().getCompanyName();
        bookingDetailDTO.parkingAddress = booking.getParking().getAddress();
        bookingDetailDTO.startDateTime= booking.getStartDateTime();
        bookingDetailDTO.endDateTime = booking.getEndDateTime();

        return bookingDetailDTO;
    }

}
