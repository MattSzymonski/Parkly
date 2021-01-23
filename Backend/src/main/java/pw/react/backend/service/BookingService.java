package pw.react.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pw.react.backend.appException.InvalidFileException;
import pw.react.backend.appException.ResourceNotFoundException;
import pw.react.backend.dao.AddressRepository;
import pw.react.backend.dao.BookingRepository;
import pw.react.backend.model.Address;
import pw.react.backend.model.Booking;
import pw.react.backend.model.BooklyBooking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService implements BookingMainService {

    private final BookingRepository repository;
    private ParkingService parkingService;

    @Autowired
    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setParkingService(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @Override
    public Booking addBooking(Booking address) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Booking> convertToBookings(List<BooklyBooking> booklyBookings) {
        List<Booking> bookings = new ArrayList<Booking>();

        for (BooklyBooking booklyBooking : booklyBookings) {
            bookings.add(new Booking(booklyBooking.getBooklyUserId(), parkingService.findById(booklyBooking.getParkingId()), booklyBooking.getStartDateTime(), booklyBooking.getEndDateTime()));
        }

        return bookings;
    }
}