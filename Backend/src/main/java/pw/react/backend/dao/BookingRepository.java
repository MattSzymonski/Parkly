package pw.react.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pw.react.backend.model.Booking;
import pw.react.backend.model.BooklyBooking1;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



public interface BookingRepository extends JpaRepository<Booking, Long> {

    //@Query("select new com.bytestree.restful.dto.CustomEmployeeRs(e.firstName, e.lastName, e.department.name) from Employee e")
    //List<BooklyBooking1> findByStartDateTime(LocalDateTime startDateTime);
   // List<BooklyBooking1> findAllWithCustomObject();
    //Optional<Booking> findByCompanyName(String companyName);
}
