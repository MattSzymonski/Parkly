package pw.react.backend.service;

import pw.react.backend.model.data.Parking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ParkingMainService { 
    Page<Parking> findAll(
        Long id,
        String name,
        Integer minimumSpotsTotal,
        String companyName,
        String country,
        String town,
        String streetName,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        Pageable pageable);
    Parking findById(long parkingId);
    Parking updateById(Long parkingId, Parking updatedParking);
    boolean deleteById(Long parkingId);
    Parking add(Parking parking);
}
