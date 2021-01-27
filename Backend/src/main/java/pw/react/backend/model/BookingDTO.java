package pw.react.backend.model;
import lombok.Getter;
import lombok.Setter;
import pw.react.backend.model.data.Booking;
import java.time.LocalDateTime;

public class BookingDTO { // This object represents one entry in boking list. It is send to Parkly frontend and Bookly backend
    
    @Getter @Setter private long id; // Booking id
    @Getter @Setter private long userId; // Booking id
    @Getter @Setter private String userFirstName;
    @Getter @Setter private String userLastName;
    @Getter @Setter private long parkingId;
    @Getter @Setter private String parkingName;
    @Getter @Setter private LocalDateTime startDateTime;
    @Getter @Setter private LocalDateTime endDateTime;

    public BookingDTO() { }

    public static BookingDTO createBookingDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.id = booking.getId();
        bookingDTO.userId = booking.getUserId();
        bookingDTO.userFirstName = booking.getUserFirstName();
        bookingDTO.userLastName = booking.getUserLastName();
        bookingDTO.parkingId = booking.getParking().getId();
        bookingDTO.parkingName = booking.getParking().getName();
        bookingDTO.startDateTime= booking.getStartDateTime();
        bookingDTO.endDateTime = booking.getEndDateTime();

        return bookingDTO;
    }

}
