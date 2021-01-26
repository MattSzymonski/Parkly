package pw.react.backend.service;

import java.util.List;

import pw.react.backend.model.Booking;
import pw.react.backend.model.bookly.BooklyBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingMainService {
    Booking addBooking(BooklyBooking booklyBooking);  
    Page<Booking> findAll(
        //String nameKeyword, 
        //Integer spotsTotalKeyword,
        Pageable pageable);
    boolean deleteBookingById(Long bookingId);
}
