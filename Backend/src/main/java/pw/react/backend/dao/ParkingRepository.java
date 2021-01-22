package pw.react.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.react.backend.model.Parking;
import pw.react.backend.model.Address;
import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Parking, Long> {
    Optional<Parking> findByAddressId(Long addressId);
    Optional<Parking> findByIdAndAddressId(Long id, Long addressId);



    //void deleteByParkingId(long parkingId);
}
