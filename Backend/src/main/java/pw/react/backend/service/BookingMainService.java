package pw.react.backend.service;

import pw.react.backend.model.data.Booking;
import pw.react.backend.model.BookingDetailDTO;
import pw.react.backend.model.bookly.BooklyBooking;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingMainService {
    Booking add(BooklyBooking booklyBooking);

    Booking findById(long bookingId);

    Page<Booking> findAll(
            Long id,
            Long userId,
            String userFirstName, 
            String userLastName, 
            Long parkingId, 
            String parkingName, 
            LocalDateTime startDateTime, 
            LocalDateTime endDateTime, 
            Pageable pageable);

    boolean deleteById(Long bookingId);

    BookingDetailDTO createBookingDetailedDTO(Booking booking);
}
