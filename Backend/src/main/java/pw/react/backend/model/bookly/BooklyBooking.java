package pw.react.backend.model.bookly;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


public class BooklyBooking { // Reflects booking request from Bookly system
    
    @ApiModelProperty(position = 1)
    @Getter @Setter private long userId; // Bookly user id

    @ApiModelProperty(position = 2)
    @Getter @Setter private String userFirstName;

    @ApiModelProperty(position = 3)
    @Getter @Setter private String userLastName;

    @ApiModelProperty(position = 4)
    @Getter @Setter private long parkingId;

    @ApiModelProperty(position = 5)
    @Getter @Setter private LocalDateTime startDateTime;

    @ApiModelProperty(position = 6)
    @Getter @Setter private LocalDateTime endDateTime; 

}
