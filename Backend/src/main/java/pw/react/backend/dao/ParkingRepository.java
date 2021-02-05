package pw.react.backend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pw.react.backend.model.data.Parking;
import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Parking, Long> {
    Optional<Parking> findByAddressId(Long addressId);
    Optional<Parking> findByIdAndAddressId(Long id, Long addressId);

    public String findAllQuery = "SELECT p FROM Parking p WHERE"
    + "(:name is null or p.name LIKE %:name%)"
    + "AND (:companyName is null or p.parkingOwner.companyName = :companyName)";

    @Query(value = findAllQuery)
    public Page<Parking> findAll(
        @Param("name") String name, 
        @Param("companyName") String companyName,
        Pageable pageable);
}
