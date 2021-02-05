package pw.react.backend.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pw.react.backend.dao.BookingRepository;
import pw.react.backend.model.data.Booking;
import pw.react.backend.model.data.Parking;
import pw.react.backend.model.BookingDetailDTO;
import pw.react.backend.model.bookly.BooklyBooking;
import pw.react.backend.model.bookly.BooklyUser;

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
    public Booking add(BooklyBooking booklyBooking) {
        Parking parking = parkingService.findById(booklyBooking.getParkingId());
        if (parking == null) {
            return null;
        }

        return repository.save(new Booking(booklyBooking, parking));
    }

    @Override
    public Page<Booking> findAll(
            Long id,
            Long userId,
            String userFirstName, 
            String userLastName, 
            Long parkingId, 
            String parkingName, 
            LocalDateTime startDateTime, 
            LocalDateTime endDateTime, 
            Pageable pageable) {
        return repository.findAll(
                id,
                userId,
                userFirstName,
                userLastName,
                parkingId,
                parkingName,
                startDateTime,
                endDateTime,
                pageable);
    }

    @Override
    public boolean deleteById(Long bookingId) {
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

        return new BookingDetailDTO(booking, booklyUser);
    }
}

