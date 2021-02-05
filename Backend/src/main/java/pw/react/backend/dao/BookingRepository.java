package pw.react.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pw.react.backend.model.data.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    public String findAllQuery = "SELECT b FROM Booking b WHERE"
    + "(:parkingId is null or b.parking.id = :parkingId)";

    @Query(value = findAllQuery) 
    public Page<Booking> findAll(
        Long parkingId,
        Pageable pageable);
}
