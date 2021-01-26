package pw.react.backend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    
    //@Query(value = "SELECT p FROM Parking p WHERE (:name is null or p.name LIKE %:name%) AND (:spotsTotal is null or p.spotsTotal = :spotsTotal)", countQuery = "SELECT count(*) FROM Parking", nativeQuery = true)
    //public List<Parking> findAll(@Param("name") String name, @Param("spotsTotal")Integer spotsTotal, Pageable pageable);

    @Query(value = "SELECT p FROM Parking p WHERE (:name is null or p.name LIKE %:name%) AND (:spotsTotal is null or p.spotsTotal = :spotsTotal)")
    public Page<Parking> findAll(
        @Param("name") String name, 
        @Param("spotsTotal")Integer spotsTotal, 
        Pageable pageable);


    //List<Parking> findByNameContaining(String name, Pageable pageable);
    //Page<Parking> findByNameContaining(String name, Pageable pageable);
   
    //public List<Parking> findAll(Sort.by("name"));
    //void deleteByParkingId(long parkingId);
}
