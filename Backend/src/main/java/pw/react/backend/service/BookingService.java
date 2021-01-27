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
import pw.react.backend.model.data.Address;
import pw.react.backend.model.data.Booking;
import pw.react.backend.model.data.Parking;
import pw.react.backend.model.BookingDetailDTO;
import pw.react.backend.model.bookly.BooklyBooking;
import pw.react.backend.model.bookly.BooklyUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService implements BookingMainService {

    private final Logger logger = LoggerFactory.getLogger(AddressService.class);

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
        Parking parking = parkingService.findById(booklyBooking.getParkingId());
        if (parking == null) {
            return null;
        }

        return repository.save(Booking.createBooking(booklyBooking, parking));
    }

    @Override
    public Page<Booking> findAll(Long parkingId,
            // String name,
            // Integer spotsTotal,
            Pageable pageable) {
        return repository.findAll(parkingId,
                // name,
                // spotsTotal,
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

    @Override
    public Booking findById(long bookingId) {
        return repository.findById(bookingId).orElseGet(() -> null);
    }

    @Override
    public BookingDetailDTO createBookingDetailedDTO(Booking booking) {
        
        // TODO: fetch user data from Bookly !!!!!!!!!!
        // If failed to fetch then return null

        BooklyUser booklyUser = new BooklyUser(); // Mock user
        booklyUser.setFirstName(booking.getUserFirstName()); // This is not needed because first name is saved in booking already
        booklyUser.setLastName(booking.getUserLastName());  // This is not needed because last name is saved in booking already
        booklyUser.setPhoneNumber("MOCK_PHONE");
        booklyUser.setEmailAddress("MOCK_EMAIL_ADDRESS");
        booklyUser.setRegistrationPlates("MOCK_REGISTRATION_PLATES");

        return BookingDetailDTO.createBookingDetailDTO(booking, booklyUser);
    }
}

