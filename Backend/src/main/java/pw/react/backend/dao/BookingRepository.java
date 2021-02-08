package pw.react.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pw.react.backend.model.data.Booking;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    public String findAllQuery = "SELECT b FROM Booking b WHERE"
    + "(:id is null or b.id = :id)"
    + "AND (:userId is null or b.userId = :userId)"
    + "AND (:userFirstName is null or b.userFirstName LIKE %:userFirstName%)"
    + "AND (:userLastName is null or b.userLastName LIKE %:userLastName%)"
    + "AND (:parkingId is null or b.parking.id = :parkingId)"
    + "AND (:parkingName is null or b.parking.name LIKE %:parkingName%)"
    + "AND (:startDateTime is null or b.endDateTime > :startDateTime)"
    + "AND (:endDateTime is null or b.startDateTime < :endDateTime)";

    @Query(value = findAllQuery) 
    public Page<Booking> findAll(
        @Param("id") Long id,
        @Param("userId") Long userId,
        @Param("userFirstName") String userFirstName,
        @Param("userLastName") String userLastName,
        @Param("parkingId") Long parkingId,
        @Param("parkingName")  String parkingName,
        @Param("startDateTime") LocalDateTime startDateTime,
        @Param("endDateTime")LocalDateTime endDateTime,
        Pageable pageable);


    public String findIndicesByParkingIdQuery = "SELECT b.id FROM Booking b WHERE"
    + "(:parkingId is null or b.parking.id = :parkingId)";

    @Query(value = findIndicesByParkingIdQuery) 
    public ArrayList<Long> findIndicesByParkingId(@Param("parkingId") long parkingId);


    public String findBookingCountForParkingId = "SELECT COUNT(b.parking.id) AS c FROM Booking b WHERE b.parking.id = :parkingId "
    + " AND b.endDateTime > :startDateTime AND b.startDateTime < :endDateTime";
    
    @Query(value = findBookingCountForParkingId) 
	public int checkBookingCountForParkingId(long parkingId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}