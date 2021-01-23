package pw.react.backend.service;

import pw.react.backend.model.BooklyBooking;
import pw.react.backend.model.Parking;

import java.util.List;
import java.util.Optional;

public interface ParkingMainService {
    Parking updateParking(Long id, Parking updatedCompany);
    boolean deleteParking(Long companyId);
    Optional<Parking> findById(long parkingId);
    List<Parking> findAll(String nameKeyword, Integer spotsTotalKeyword);
}
