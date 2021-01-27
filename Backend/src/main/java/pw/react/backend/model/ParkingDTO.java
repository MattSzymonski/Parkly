package pw.react.backend.model;

import lombok.Getter;
import lombok.Setter;
import pw.react.backend.model.data.Address;
import pw.react.backend.model.data.Parking;


public class ParkingDTO { // This object represents one entry in parking list. It is send to Parkly frontend and Bookly backend
    
    @Getter @Setter private long id; // Parking id
    @Getter @Setter private String name;
    @Getter @Setter private Integer spotsTaken;
    @Getter @Setter private Integer spotsTotal;
    @Getter @Setter private String ownerCompanyName;
    @Getter @Setter private Address address;

    public ParkingDTO() {
    }

    public static ParkingDTO CreateParkingDTO(Parking parking) {
        ParkingDTO parkingDTO = new ParkingDTO();

        parkingDTO.id = parking.getId();
        parkingDTO.name = parking.getName();
        parkingDTO.spotsTaken = parking.getSpotsTaken();
        parkingDTO.spotsTotal = parking.getSpotsTotal();
        parkingDTO.ownerCompanyName = parking.getParkingOwner().getCompanyName();
        parkingDTO.address = parking.getAddress();

        return parkingDTO;
    }
}
