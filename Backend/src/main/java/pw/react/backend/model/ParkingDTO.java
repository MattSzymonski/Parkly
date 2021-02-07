package pw.react.backend.model;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import pw.react.backend.model.data.Address;
import pw.react.backend.model.data.Parking;


public class ParkingDTO { // This object represents one entry in parking list. It is send to Parkly frontend and Bookly backend
    
    @ApiModelProperty(position = 1)
    @Getter @Setter private long id; // Parking id

    @ApiModelProperty(position = 2)
    @Getter @Setter private String name;

    @ApiModelProperty(position = 3)
    @Getter @Setter private Integer spotsTaken; // Taken in given time interval

    @ApiModelProperty(position = 4)
    @Getter @Setter private Integer spotsTotal;

    @ApiModelProperty(position = 5)
    @Getter @Setter private Float pricePerHour;

    @ApiModelProperty(position = 6)
    @Getter @Setter private Float totalPrice;

    @ApiModelProperty(position = 7)
    @Getter @Setter private String ownerCompanyName;

    @ApiModelProperty(position = 8)
    @Getter @Setter private Address address;

    @ApiModelProperty(position = 9)
    @Getter @Setter private LocalDateTime addedDateTime;

    public ParkingDTO(Parking parking, Float totalPrice){
        this.id = parking.getId();
        this.name = parking.getName();
        this.spotsTaken = 0;
        this.spotsTotal = parking.getSpotsTotal();  
        this.pricePerHour = parking.getPricePerHour();
        this.totalPrice = totalPrice;  
        this.ownerCompanyName = parking.getParkingOwner().getCompanyName();
        this.address = parking.getAddress();
        this.addedDateTime = parking.getAddedDateTime();
    }
}
