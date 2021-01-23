package pw.react.backend.service;

import java.util.List;

import pw.react.backend.model.Booking;
import pw.react.backend.model.BooklyBooking;

public interface BookingMainService {
    Booking addBooking(Booking address);
    List<Booking> convertToBookings(List<BooklyBooking> booklyBookings) ;
    
}
