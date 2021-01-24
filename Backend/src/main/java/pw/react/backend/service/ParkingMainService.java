package pw.react.backend.service;

import pw.react.backend.model.BooklyBooking;
import pw.react.backend.model.Parking;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParkingMainService { 
    Page<Parking> findAll(String nameKeyword, Integer spotsTotalKeyword, Pageable pageable);
    Parking findById(long parkingId);
    Parking updateParkingById(Long parkingId, Parking updatedParking);
    boolean deleteParkingById(Long parkingId);
    Parking addParking(Parking parking);
}
