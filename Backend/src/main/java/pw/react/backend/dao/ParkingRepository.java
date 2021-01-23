package pw.react.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pw.react.backend.model.Parking;
import pw.react.backend.model.Address;

import java.util.List;
import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Parking, Long> {
    Optional<Parking> findByAddressId(Long addressId);
    Optional<Parking> findByIdAndAddressId(Long id, Long addressId);

    @Query(value = "SELECT p FROM Parking p WHERE (:name is null or p.name LIKE %:name%) AND (:spotsTotal is null or p.spotsTotal = :spotsTotal)")
    public List<Parking> findAll(@Param("name") String name, @Param("spotsTotal")Integer spotsTotal);
    
    //void deleteByParkingId(long parkingId);
}
