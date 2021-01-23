package pw.react.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.react.backend.model.Booking;
import java.util.Optional;



public interface BookingRepository extends JpaRepository<Booking, Long> {
    //Optional<Booking> findByCompanyName(String companyName);
}
