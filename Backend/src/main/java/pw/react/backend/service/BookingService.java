package pw.react.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pw.react.backend.appException.InvalidFileException;
import pw.react.backend.appException.ResourceNotFoundException;
import pw.react.backend.dao.AddressRepository;
import pw.react.backend.dao.BookingRepository;
import pw.react.backend.model.Address;
import pw.react.backend.model.Booking;
import pw.react.backend.model.bookly.BooklyBooking;

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
    public Booking addBooking(BooklyBooking booklyBooking) {
        if (parkingService.findById(booklyBooking.getParkingId()) == null) {
            return null;
        }
        return repository
                .save(new Booking(booklyBooking.getUserId(), parkingService.findById(booklyBooking.getParkingId()),
                        booklyBooking.getStartDateTime(), booklyBooking.getEndDateTime()));
    }

    @Override
    public Page<Booking> findAll(
        //String nameKeyword,
        //Integer spotsTotalKeyword,
        Pageable pageable
    ) {
        return repository.findAll(
            //nameKeyword, 
            //spotsTotalKeyword,
            pageable);
    }

    @Override
    public boolean deleteBookingById(Long bookingId) {
        boolean result = false;
        if (repository.existsById(bookingId)) {
            repository.deleteById(bookingId);
            result = true;
        }
        return result;
    }
}

