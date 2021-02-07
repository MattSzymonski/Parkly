package pw.react.backend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pw.react.backend.model.data.Parking;
import java.util.Optional;


import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public interface ParkingRepository extends JpaRepository<Parking, Long> {
    Optional<Parking> findByAddressId(Long addressId);
    Optional<Parking> findByIdAndAddressId(Long id, Long addressId);

    public String findAllQuery =
    "SELECT p FROM Parking p "
    + "JOIN Booking b ON b.parking.id = p.id "
    + "WHERE "
    + "(:id is null or p.id = :id) "
    + "AND (:name is null or p.name LIKE %:name%) "
    + "AND (:minimumSpotsTotal is null or p.spotsTotal >= :minimumSpotsTotal) "
    + "AND (:companyName is null or p.parkingOwner.companyName LIKE %:companyName%) "
    + "AND (:country is null or p.address.country LIKE %:country%) "
    + "AND (:town is null or p.address.town LIKE %:town%) "
    + "AND (:streetName is null or p.address.streetName LIKE %:streetName%) "
    + "AND (:startDateTime is null or b.endDateTime > :startDateTime) "
    + "AND (:endDateTime is null or b.startDateTime < :endDateTime) "
    + "GROUP BY p.id "
    + "HAVING COUNT(*) < p.spotsTotal";

   @Query(value = findAllQuery)
    public Page<Parking> findAll(
        @Param("id") Long id,
        @Param("name") String name,
        @Param("minimumSpotsTotal") Integer minimumSpotsTotal,
        @Param("companyName") String companyName,
        @Param("country") String country,
        @Param("town") String town,
        @Param("streetName") String streetName,
        @Param("startDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
        @Param("endDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime,
        Pageable pageable);
    
}

