package pw.react.backend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pw.react.backend.model.data.Parking;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Parking, Long> {
    Optional<Parking> findByAddressId(Long addressId);
    Optional<Parking> findByIdAndAddressId(Long id, Long addressId);

    public String findAllQuery = "SELECT p FROM Parking p WHERE"
    + "(:id is null or p.id = :id)"
    + "AND (:name is null or p.name LIKE %:name%)"
    + "AND (:minimumSpotsTotal is null or p.spotsTotal >= :minimumSpotsTotal)"
    + "AND (:companyName is null or p.parkingOwner.companyName LIKE %:companyName%)"
    + "AND (:country is null or p.address.country LIKE %:country%)"
    + "AND (:town is null or p.address.town LIKE %:town%)"
    + "AND (:streetName is null or p.address.streetName LIKE %:streetName%)";
    // Add finding parkings with free spots in given time interval to this query

    @Query(value = findAllQuery)
    public Page<Parking> findAll(
        @Param("id") Long id,
        @Param("name") String name,
        @Param("minimumSpotsTotal") Integer minimumSpotsTotal,
        @Param("companyName") String companyName,
        @Param("country") String country,
        @Param("town") String town,
        @Param("streetName") String streetName,
        // @Param("startDateTime") LocalDateTime startDateTime,
        // @Param("endDateTime") LocalDateTime endDateTime, 
        Pageable pageable);




}

