package pw.react.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ExampleProperty;
import io.swagger.models.Response;
import io.swagger.annotations.Example;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pw.react.backend.appException.ExceptionDetails;
import pw.react.backend.appException.UnauthorizedException;
import pw.react.backend.dao.BookingRepository;
import pw.react.backend.model.data.Booking;
import pw.react.backend.service.*;
import pw.react.backend.model.BookingDTO;
import pw.react.backend.model.BookingDetailDTO;
import pw.react.backend.model.bookly.BooklyBooking;
import java.util.List;
import pw.react.backend.model.PageDTO;

import static java.util.stream.Collectors.joining;

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
        @RequestParam(required = false) Long parkingId, 
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int pageSize
    ){
        logHeaders(headers);
        if (securityService.isAuthorized(headers)) 
        {
                List<BookingDTO> bookings = new ArrayList<BookingDTO>();
                Pageable paging = PageRequest.of(page, pageSize);
                Page<Booking> pageBookings = bookingService.findAll(
                    parkingId,
                    //name, 
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
            boolean deleted = bookingService.deleteBookingById(bookingId);
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
        @RequestParam(required = false) Long parkingId, 
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int pageSize
    ){
        if (securityService.isAuthorized(apiKey)) { // Bookly autorization is done using apiKey passed as parameter  
                List<BookingDTO> bookings = new ArrayList<BookingDTO>();
                Pageable paging = PageRequest.of(page, pageSize);
                Page<Booking> pageBookings = bookingService.findAll(
                    parkingId,
                    //name, 
                    paging);

                for (Booking booking : pageBookings) {
                    bookings.add(new BookingDTO(booking));
                }
                        
                PageDTO<BookingDTO> bookingsPageDTO = new PageDTO<BookingDTO>(pageBookings.getNumber(), bookings, pageBookings.getTotalElements(),pageBookings.getTotalPages());
                return ResponseEntity.ok(bookingsPageDTO);
        }
        throw new UnauthorizedException("Request is unauthorized");
    }


    // @ApiResponses(value = {
    //     @ApiResponse(code = 200, message = "Successfully retrieved list", responseContainer = "HttpStatus"),
    //     @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
    //     @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
    //     @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    // })
    @ApiOperation(value = "Description")
    // @ApiResponses(value = {
    //     @ApiResponse(code = 404, message = "Not Found",
    //             examples = @Example(
    //                     value = {
    //                             @ExampleProperty(
    //                                     mediaType = "Example json",
    //                                     value = "{\"invalidField\": \"address\"}"),
    //                             @ExampleProperty(
    //                                     mediaType = "Example string",
    //                                     value = "The first name was invalid")}),
    // response = Response.class)})
    
    @PostMapping(path = "/b/bookings") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", examples = @Example({@ExampleProperty(mediaType = "*/*", value = "35 (id of created booking)")})),
        @ApiResponse(code = 400, message = "Bad Request", examples = @Example({@ExampleProperty(mediaType = "*/*", value = "Parking with id 34 does not exists")})),
        @ApiResponse(code = 401, message = "Unauthorized", examples = @Example({@ExampleProperty(mediaType = "*/*", value = "Request is unauthorized")})) 
    })
    public ResponseEntity<String> createBookingBookly( 
        @RequestHeader HttpHeaders headers, 
        @RequestParam(required = true) String apiKey,
        @RequestBody BooklyBooking booklyBooking
    ){ 
        if (securityService.isAuthorized(apiKey)) { // Bookly autorization is done using apiKey passed as parameter
            Booking result = bookingService.addBooking(booklyBooking);
            return result != null ? ResponseEntity.ok(String.valueOf(result.getId())) : ResponseEntity.badRequest().body(String.format("Parking with id %s does not exists", booklyBooking.getParkingId()));
        }
        //throw new UnauthorizedException("Request is unauthorized");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Request is unauthorized");
    }

    @DeleteMapping(path = "/b/bookings/{bookingId}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", examples = @Example({@ExampleProperty(mediaType = "*/*", value = "Booking with id 35 deleted")})),
        @ApiResponse(code = 400, message = "Bad Request", examples = @Example({@ExampleProperty(mediaType = "*/*", value = "Booking with id 34 does not exists")})),
        @ApiResponse(code = 401, message = "Unauthorized", examples = @Example({@ExampleProperty(mediaType = "*/*", value = "Request is unauthorized")})) 
    })
    public ResponseEntity<String> deleteBookingBookly(
        @RequestHeader HttpHeaders headers, 
        @RequestParam(required = true) String apiKey,
        @PathVariable Long bookingId
    ){
        if (securityService.isAuthorized(apiKey)) {  // Bookly autorization is done using apiKey passed as parameter
            boolean deleted = bookingService.deleteBookingById(bookingId);
            if (!deleted) {
                return ResponseEntity.badRequest().body(String.format("Booking with id %s does not exists", bookingId));
            }
            return ResponseEntity.ok(String.format("Booking with id %s deleted", bookingId));
        }
        throw new UnauthorizedException("Request is unauthorized");
    }
}
