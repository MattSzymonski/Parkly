package pw.react.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pw.react.backend.model.Booking;
import pw.react.backend.model.BooklyBooking1;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    //@Query(value = "SELECT p FROM Booking p WHERE (:name is null or p.name LIKE %:name%) AND (:spotsTotal is null or p.spotsTotal = :spotsTotal)")
    @Query(value = "SELECT p FROM Booking p")
    public Page<Booking> findAll(
        //@Param("name") String name, 
        //@Param("spotsTotal")Integer spotsTotal, 
        Pageable pageable);


    //@Query("select new com.bytestree.restful.dto.CustomEmployeeRs(e.firstName, e.lastName, e.department.name) from Employee e")
    //List<BooklyBooking1> findByStartDateTime(LocalDateTime startDateTime);
   // List<BooklyBooking1> findAllWithCustomObject();
    //Optional<Booking> findByCompanyName(String companyName);
}
