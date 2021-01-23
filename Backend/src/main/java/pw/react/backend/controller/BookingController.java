package pw.react.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pw.react.backend.appException.UnauthorizedException;
import pw.react.backend.dao.ParkingRepository;
import pw.react.backend.dao.BookingRepository;
import pw.react.backend.model.Booking;
import pw.react.backend.dao.ParkingOwnerRepository;
import pw.react.backend.model.Address;
import pw.react.backend.model.Parking;
import pw.react.backend.model.ParkingOwner;
import pw.react.backend.service.*;
import pw.react.backend.model.BooklyBooking;
import pw.react.backend.model.BooklyBooking1;
import pw.react.backend.web.UploadFileResponse;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import static java.util.stream.Collectors.joining;



@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    private final Logger logger = LoggerFactory.getLogger(ParkingController.class);

    private final BookingRepository repository;
    private final BookingService bookingService;
    private final SecurityProvider securityService;

    // private final ParkingService parkingService;
    // private AddressService addressService;
    // private ParkingOwnerService parkingOwnerService;

    @Autowired
    public BookingController(BookingRepository repository, SecurityProvider securityService, BookingService bookingService) {
        this.repository = repository;
        this.securityService = securityService;
        this.bookingService = bookingService; 
    }

    // @Autowired
    // public void setAddressService(AddressService addressService) {
    //     this.addressService = addressService;
    // }

    // @Autowired
    // public void setParkingOwnerService(ParkingOwnerService parkingOwnerService) {
    //     this.parkingOwnerService = parkingOwnerService;
    // }

    private void logHeaders(@RequestHeader HttpHeaders headers) {
        logger.info("Controller request headers {}",
                headers.entrySet()
                        .stream()
                        .map(entry -> String.format("%s->[%s]", entry.getKey(), String.join(",", entry.getValue())))
                        .collect(joining(","))
        );
    }

    @PostMapping(path = "") // For Bookly
    public ResponseEntity<String> createBookings(@RequestHeader HttpHeaders headers, @RequestBody List<BooklyBooking> booklyBookings) { 
        logHeaders(headers);
        if (securityService.isAuthorized(headers)) {
            List<Booking> result = repository.saveAll(bookingService.convertToBookings(booklyBookings));
            return ResponseEntity.ok(result.stream().map(c -> String.valueOf(c.getId())).collect(joining(",")));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Request is unauthorized");
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

    @GetMapping(path = "") // For Parkly
    public ResponseEntity<Collection<Booking>> getAllBookings(@RequestHeader HttpHeaders headers) {
        logHeaders(headers);
        if (securityService.isAuthorized(headers)) {
            return ResponseEntity.ok(repository.findAll());
        }
        throw new UnauthorizedException("Request is unauthorized");
    }

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
