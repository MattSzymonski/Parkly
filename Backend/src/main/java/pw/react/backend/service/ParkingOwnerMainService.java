package pw.react.backend.service;

import pw.react.backend.model.data.ParkingOwner;

public interface ParkingOwnerMainService {
    ParkingOwner add(ParkingOwner parkingOwner, boolean reuseIfExists);
}
