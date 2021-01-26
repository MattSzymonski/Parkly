package pw.react.backend.model.bookly;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


public class BooklyBooking { // Reflects booking request from Bookly system
    
    @Getter @Setter private long userId; // Bookly user id
    @Getter @Setter private long parkingId;
    @Getter @Setter private LocalDateTime startDateTime;
    @Getter @Setter private LocalDateTime endDateTime; 

}
