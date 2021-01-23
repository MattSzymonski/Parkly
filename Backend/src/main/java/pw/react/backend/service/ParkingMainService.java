package pw.react.backend.service;

import pw.react.backend.model.BooklyBooking;
import pw.react.backend.model.Parking;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParkingMainService {
    Parking updateParking(Long id, Parking updatedCompany);
    boolean deleteParking(Long companyId);
    Optional<Parking> findById(long parkingId);
    Page<Parking> findAll(String nameKeyword, Integer spotsTotalKeyword, Pageable pageable);
}
