package pw.react.backend.service;

import pw.react.backend.model.Parking;

public interface ParkingService {
    Parking updateParking(Long id, Parking updatedCompany);
    boolean deleteParking(Long companyId);
}
