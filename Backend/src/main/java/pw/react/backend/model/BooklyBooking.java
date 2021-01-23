package pw.react.backend.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


public class BooklyBooking { // Used as type for Parkly - Bookly communication (does not contain parking data, just its id)
    
    @Getter @Setter private long booklyUserId;
    @Getter @Setter private long parkingId;
    @Getter @Setter private LocalDateTime startDateTime;
    @Getter @Setter private LocalDateTime endDateTime; 

    // public BooklyBooking(long BooklyBooking, long parkingId,  LocalDateTime startDateTime, LocalDateTime endDateTime) {
    //     x = 5;  // Set the initial value for the class attribute x
    // }
}
