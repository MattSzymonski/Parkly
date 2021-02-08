package pw.react.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import pw.react.backend.appException.InvalidRequestException;
import pw.react.backend.appException.UnauthorizedException;
import pw.react.backend.dao.BookingRepository;
import pw.react.backend.model.data.Booking;
import pw.react.backend.service.*;
import pw.react.backend.model.BookingDTO;
import pw.react.backend.model.BookingDetailDTO;
import pw.react.backend.model.bookly.BooklyBooking;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pw.react.backend.model.PageDTO;

import static java.util.stream.Collectors.joining;

import java.time.LocalDateTime;
import java.util.ArrayList;



@RestController
//@RequestMapping(path = "/bookings")
public class BookingController {

    private final Logger logger = LoggerFactory.getLogger(ParkingController.class);

    private final BookingService bookingService;
    private final SecurityService securityService;

    @Autowired
    public BookingController(BookingRepository repository, SecurityService securityService, BookingService bookingService) {
        this.securityService = securityService;
        this.bookingService = bookingService; 
    }

    private void logHeaders(@RequestHeader HttpHeaders headers) {
        logger.info("Controller request headers {}",
                headers.entrySet()
                        .stream()
                        .map(entry -> String.format("%s->[%s]", entry.getKey(), String.join(",", entry.getValue())))
                        .collect(joining(","))
        );
    }

    @GetMapping(path = "/p/bookings")
    public ResponseEntity<PageDTO<BookingDTO>> getAllBookings(
        @RequestHeader HttpHeaders headers, 

        @RequestParam(required = false) Long id,
        @RequestParam(required = false) Long userId,
        @RequestParam(required = false) String userFirstName,
        @RequestParam(required = false) String userLastName,
        @RequestParam(required = false) Long parkingId,
        @RequestParam(required = false) String parkingName,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime,

        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int pageSize
    ){
        logHeaders(headers);
        if (securityService.isAuthorized(headers)) 
        {
                List<BookingDTO> bookings = new ArrayList<BookingDTO>();
                Pageable paging = PageRequest.of(page, pageSize);
                Page<Booking> pageBookings = bookingService.findAll(
                    id,
                    userId,
                    userFirstName,
                    userLastName,
                    parkingId,
                    parkingName,
                    startDateTime,
                    endDateTime,
                    paging);

                for (Booking booking : pageBookings) {
                    bookings.add(new BookingDTO(booking));
                }
                PageDTO<BookingDTO> bookingsPageDTO = new PageDTO<BookingDTO>(pageBookings.getNumber(), bookings, pageBookings.getTotalElements(),pageBookings.getTotalPages());
                return ResponseEntity.ok(bookingsPageDTO);
        }
        throw new UnauthorizedException("Request is unauthorized");
    }


    @GetMapping(path = "/p/bookings/{bookingId}")
    public ResponseEntity<BookingDetailDTO> getBookingById(@RequestHeader HttpHeaders headers, @PathVariable Long bookingId) {
        logHeaders(headers);
        if (securityService.isAuthorized(headers)) {

            Booking booking = bookingService.findById(bookingId);

            if (booking == null) {
                return ResponseEntity.badRequest().body(BookingDetailDTO.EMPTY);
            }

            BookingDetailDTO bookingDetailedDTO = bookingService.createBookingDetailedDTO(booking);
            return bookingDetailedDTO != null ? ResponseEntity.ok(bookingDetailedDTO) : ResponseEntity.badRequest().body(BookingDetailDTO.EMPTY);
        }
        throw new UnauthorizedException("Request is unauthorized");
    }


    @DeleteMapping(path = "/p/bookings/{bookingId}")
    public ResponseEntity<String> deleteBooking(@RequestHeader HttpHeaders headers, @PathVariable Long bookingId) {
        logHeaders(headers);
        if (securityService.isAuthorized(headers)) {
            boolean deleted = bookingService.deleteById(bookingId);
            if (!deleted) {
                return ResponseEntity.badRequest().body(String.format("Booking with id %s does not exists", bookingId));
            }
            return ResponseEntity.ok(String.format("Booking with id %s deleted", bookingId));
        }
        throw new UnauthorizedException("Request is unauthorized");
    }


    // ---------- Bookly API ----------

    @GetMapping(path = "/b/bookings")
    public ResponseEntity<PageDTO<BookingDTO>> getAllBookingsBookly(
        @RequestHeader HttpHeaders headers, 
        @RequestParam(required = true) String apiKey, 

        @RequestParam(required = false) Long id,
        @RequestParam(required = false) Long userId,
        @RequestParam(required = false) String userFirstName,
        @RequestParam(required = false) String userLastName,
        @RequestParam(required = false) Long parkingId,
        @RequestParam(required = false) String parkingName,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime,

        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int pageSize
    ){
        if (securityService.isAuthorized(apiKey)) // Bookly autorization is done using apiKey passed as parameter
        {
                List<BookingDTO> bookings = new ArrayList<BookingDTO>();
                Pageable paging = PageRequest.of(page, pageSize);
                Page<Booking> pageBookings = bookingService.findAll(
                    id,
                    userId,
                    userFirstName,
                    userLastName,
                    parkingId,
                    parkingName,
                    startDateTime,
                    endDateTime,
                    paging);

                for (Booking booking : pageBookings) {
                    bookings.add(new BookingDTO(booking));
                }
                        
                PageDTO<BookingDTO> bookingsPageDTO = new PageDTO<BookingDTO>(pageBookings.getNumber(), bookings, pageBookings.getTotalElements(),pageBookings.getTotalPages());
                return ResponseEntity.ok(bookingsPageDTO);
        }
        throw new UnauthorizedException("Request is unauthorized");
    }

    @PostMapping(path = "/b/bookings") 
    public ResponseEntity<BookingDTO> createBookingBookly( 
        @RequestHeader HttpHeaders headers, 
        @RequestParam(required = true) String apiKey,
        @RequestBody BooklyBooking booklyBooking
    ){ 
        if (securityService.isAuthorized(apiKey)) { // Bookly autorization is done using apiKey passed as parameter
            Booking result = bookingService.add(booklyBooking);
            if (result == null) {
                throw new InvalidRequestException(String.format("Cannot add new booking. Parking with given id does not exist or is full in given time interval"));
            }
            return ResponseEntity.ok(new BookingDTO(result));
            //return result != null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(BookingDTO.EMPTY);
        }
        throw new UnauthorizedException("Request is unauthorized");
    }

    @DeleteMapping(path = "/b/bookings/{bookingId}")
    public ResponseEntity<String> deleteBookingBookly(
        @RequestHeader HttpHeaders headers, 
        @RequestParam(required = true) String apiKey,
        @PathVariable Long bookingId
    ){
        if (securityService.isAuthorized(apiKey)) {
            boolean deleted = bookingService.deleteById(bookingId);
            if (!deleted) {
                return ResponseEntity.badRequest().body(String.format("Booking with id %s does not exists", bookingId));
            }
            return ResponseEntity.ok(String.format("Booking with id %s deleted", bookingId));
        }
        throw new UnauthorizedException("Request is unauthorized");
    }
}
