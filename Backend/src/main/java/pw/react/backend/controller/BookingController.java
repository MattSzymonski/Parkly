package pw.react.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pw.react.backend.appException.UnauthorizedException;
import pw.react.backend.dao.BookingRepository;
import pw.react.backend.model.data.Booking;
import pw.react.backend.service.*;
import pw.react.backend.model.BookingDTO;
import pw.react.backend.model.BookingDetailDTO;
import pw.react.backend.model.bookly.BooklyBooking;
import pw.react.backend.model.BookingDetailDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;



@RestController
@RequestMapping(path = "/bookings")
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

    @PostMapping(path = "") // For Bookly to create bookings
    public ResponseEntity<String> createBooking(@RequestHeader HttpHeaders headers, @RequestBody BooklyBooking booklyBooking) { 
        logHeaders(headers);
        if (securityService.isAuthorized(headers)) {

            Booking result = bookingService.addBooking(booklyBooking);
            return result != null ? ResponseEntity.ok(String.valueOf(result.getId())) : ResponseEntity.badRequest().body(String.format("Parking with id %s does not exists", booklyBooking.getParkingId()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Request is unauthorized");
    }

    @GetMapping(path = "")
    public ResponseEntity<Map<String, Object>> getAllBookings(
        @RequestHeader HttpHeaders headers, 
        @RequestParam(required = false) Long parkingId, 
        //@RequestParam(required = false) Integer spotsTotal, 
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int pageSize
    ){
        logHeaders(headers);
        if (securityService.isAuthorized(headers)) 
        {
                List<BookingDTO> bookings = new ArrayList<BookingDTO>();
                Pageable paging = PageRequest.of(page, pageSize);
                Map<String, Object> response = new HashMap<>();
                Page<Booking> pageBookings = bookingService.findAll(
                    parkingId,
                    //name, 
                    //spotsTotal,
                    paging);

                for (Booking booking : pageBookings) {
                    bookings.add(BookingDTO.createBookingDTO(booking));
                }
                           
                response.put("bookings", bookings);
                response.put("currentPage", pageBookings.getNumber());
                response.put("totalItems", pageBookings.getTotalElements());
                response.put("totalPages", pageBookings.getTotalPages());
        
                return new ResponseEntity<>(response, HttpStatus.OK);
        }
        throw new UnauthorizedException("Request is unauthorized");
    }


    @GetMapping(path = "/{bookingId}")
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


    @DeleteMapping(path = "/{bookingId}")
    public ResponseEntity<String> deleteBooking(@RequestHeader HttpHeaders headers, @PathVariable Long bookingId) {
        logHeaders(headers);
        if (securityService.isAuthorized(headers)) {
            boolean deleted = bookingService.deleteBookingById(bookingId);
            if (!deleted) {
                return ResponseEntity.badRequest().body(String.format("Booking with id %s does not exists", bookingId));
            }
            return ResponseEntity.ok(String.format("Booking with id %s deleted", bookingId));
        }
        throw new UnauthorizedException("Request is unauthorized");
    }




    // @GetMapping(path = "")
    // public ResponseEntity<Collection<String>> getAllBookings(@RequestHeader HttpHeaders headers) {
    //     logHeaders(headers);
    //     if (securityService.isAuthorized(headers)) {
    //         List<Booking> cb = repository.findAll();
           

    //         //yyyyyyyyyyyyyyyyyyyy


    //         return ResponseEntity.ok(repository.findAll());
    //     }
    //     throw new UnauthorizedException("Request is unauthorized");
    // }

    // @GetMapping(path = "") // For Parkly
    // public ResponseEntity<Collection<Booking>> getAllBookings(@RequestHeader HttpHeaders headers) {
    //     logHeaders(headers);
    //     if (securityService.isAuthorized(headers)) {
    //         return ResponseEntity.ok(repository.findAll());
    //     }
    //     throw new UnauthorizedException("Request is unauthorized");
    // }

    // @GetMapping(path = "/xxx") // For 
    // public ResponseEntity<Collection<BooklyBooking1>> getAllBooklyBookings(@RequestHeader HttpHeaders headers) {
    //     logHeaders(headers);
    //     if (securityService.isAuthorized(headers)) {
    //         return ResponseEntity.ok(repository.findAllWithCustomObject());
    //     }
    //     throw new UnauthorizedException("Request is unauthorized");
    // }

    // @DeleteMapping(path = "/{parkingId}")
    // public ResponseEntity<String> deleteParking(@RequestHeader HttpHeaders headers, @PathVariable Long parkingId) {
    //     logHeaders(headers);
    //     if (securityService.isAuthorized(headers)) {
    //         boolean deleted = parkingService.deleteParking(parkingId);
    //         if (!deleted) {
    //             return ResponseEntity.badRequest().body(String.format("Parking with id %s does not exists", parkingId));
    //         }
    //         return ResponseEntity.ok(String.format("Parking with id %s deleted", parkingId));
    //     }
    //     throw new UnauthorizedException("Request is unauthorized");
    // }
}
