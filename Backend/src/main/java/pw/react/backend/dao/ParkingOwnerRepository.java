package pw.react.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.react.backend.model.data.ParkingOwner;
import java.util.Optional;

public interface ParkingOwnerRepository extends JpaRepository<ParkingOwner, Long> {
    Optional<ParkingOwner> findByCompanyName(String companyName);
}
