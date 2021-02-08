package pw.react.backend.service;

import pw.react.backend.model.ParkingDTO;
import pw.react.backend.model.data.Parking;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ParkingMainService { 
    Page<Parking> findAll(
        Long id,
        String name,
        Integer minimumSpotsTotal,
        Float maximumPricePerHour,
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
	List<ParkingDTO> createParkingDTOs(List<Parking> parkings, LocalDateTime startDateTime, LocalDateTime endDateTime );
    int getSpotsTotalByParkingId(long parkingId);
}
