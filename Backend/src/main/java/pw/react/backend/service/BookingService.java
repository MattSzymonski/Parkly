package pw.react.backend.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
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

    @Value(value = "${bookly.users.address.endpoint}")
    private String booklyUsersAddressEndpoint;
    
    @Value(value = "${bookly.api.key}")
    private String apiKey;

    @Autowired
    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setParkingService(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @Autowired
	private HttpClient httpService;

    @Override
    @Transactional
    public Booking add(BooklyBooking booklyBooking) {
        Parking parking = parkingService.findById(booklyBooking.getParkingId());
        if (parking == null) {
            return null;
        }

        int spotsTotal = parkingService.getSpotsTotalByParkingId(booklyBooking.getParkingId());
        int bookingCount = checkBookingCountForParkingId(booklyBooking.getParkingId(), booklyBooking.getStartDateTime(), booklyBooking.getEndDateTime()); 

        if (bookingCount >= spotsTotal) {
            return null;
        }

        long hoursCount = booklyBooking.getStartDateTime().until(booklyBooking.getEndDateTime(), ChronoUnit.HOURS);
        return repository.save(new Booking(booklyBooking, parking, hoursCount));
    }

    @Override
    public Integer checkBookingCountForParkingId(long parkingId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (startDateTime == null || endDateTime == null) {
            return 0;
        }

        return repository.checkBookingCountForParkingId(parkingId, startDateTime, endDateTime); 
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

            // TODO: Send notification to Bookly here

            result = true;
        }
        return result;
    }

    @Override
    public int deleteBookingsByParkingId(long parkingId) {
        int deletedBookings = 0;
        ArrayList<Long> bookingIndices = repository.findIndicesByParkingId(parkingId);
        for (Long bookingIndex : bookingIndices) {
            deleteById(bookingIndex);
            deletedBookings++;
        }
        return deletedBookings;
    }

    @Override
    public Booking findById(long bookingId) {

        return repository.findById(bookingId).orElseGet(() -> null);
    }

    @Override
    public BookingDetailDTO createBookingDetailedDTO(Booking booking) {
        
        BooklyUser booklyUser = new BooklyUser();; 
        try {
            booklyUser = httpService.getUserData(booklyUsersAddressEndpoint, booking.getUserId(), apiKey);
        }
        catch(Exception e) { // If failed to get data from Bookly
            booklyUser.setFirstName(booking.getUserFirstName()); 
            booklyUser.setLastName(booking.getUserLastName()); 

            //booklyUser.setFirstName(booklyUser.getFirstName());
            //booklyUser.setLastName(booklyUser.getLastName());
            booklyUser.setAddress("Failed getting this data from Bookly");
            booklyUser.setCountry("Failed getting this data from Bookly");
            booklyUser.setPhoneNumber("Failed getting this data from Bookly");
            booklyUser.setEmail("Failed getting this data from Bookly");
            //booklyUser.setRegistrationPlates("Failed getting this data from Bookly");
            //return null;
        }
    
        // Mock (in case of Bookly not available)
        // BooklyUser booklyUser = new BooklyUser(); // Mock user
        // booklyUser.setFirstName(booking.getUserFirstName()); // This is not needed because first name is saved in booking already
        // booklyUser.setLastName(booking.getUserLastName());  // This is not needed because last name is saved in booking already
        // booklyUser.setPhoneNumber("MOCK_PHONE");
        // booklyUser.setEmailAddress("MOCK_EMAIL_ADDRESS");
        // booklyUser.setRegistrationPlates("MOCK_REGISTRATION_PLATES");

        return new BookingDetailDTO(booking, booklyUser);
    }

   

    
}

