package pw.react.backend.service;

import pw.react.backend.model.data.Booking;
import pw.react.backend.model.BookingDetailDTO;
import pw.react.backend.model.bookly.BooklyBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingMainService {
    Booking addBooking(BooklyBooking booklyBooking);

    Booking findById(long bookingId);

    Page<Booking> findAll(Long parkingId,
            // String name,
            // Integer spotsTotal,
            Pageable pageable);

    boolean deleteBookingById(Long bookingId);

    BookingDetailDTO createBookingDetailedDTO(Booking booking);
}
