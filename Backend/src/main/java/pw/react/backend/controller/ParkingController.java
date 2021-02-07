package pw.react.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ExampleProperty;
import io.swagger.models.Response;
import io.swagger.annotations.Example;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import pw.react.backend.appException.InvalidRequestException;
import pw.react.backend.appException.UnauthorizedException;
import pw.react.backend.model.ParkingDTO;
import pw.react.backend.model.PageDTO;
import pw.react.backend.model.data.Address;
import pw.react.backend.model.data.Parking;
import pw.react.backend.model.data.ParkingOwner;
import pw.react.backend.service.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import static java.util.stream.Collectors.joining;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Optional;


@RestController
public class ParkingController {

    private final Logger logger = LoggerFactory.getLogger(ParkingController.class);

    private final SecurityService securityService;
    private final ParkingService parkingService;
    private AddressService addressService;
    private ParkingOwnerService parkingOwnerService;

    @Autowired
    public ParkingController(SecurityService securityService, ParkingService parkingService, AddressService addressService) {
        this.securityService = securityService;
        this.parkingService = parkingService; 
    }

    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    @Autowired
    public void setParkingOwnerService(ParkingOwnerService parkingOwnerService) {
        this.parkingOwnerService = parkingOwnerService;
    }

    private void logHeaders(@RequestHeader HttpHeaders headers) {
        logger.info("Controller request headers {}",
                headers.entrySet()
                        .stream()
                        .map(entry -> String.format("%s->[%s]", entry.getKey(), String.join(",", entry.getValue())))
                        .collect(joining(","))
        );
    }

    @PostMapping(path = "/p/parkings")
    public ResponseEntity<String> createParkings(@RequestHeader HttpHeaders headers, @RequestBody Parking parking) { // In arguments are things that will be required to send in post request
        if (securityService.isAuthorized(headers)) {

            if (parkingService.findById(parking.getId()) != null) {
                throw new InvalidRequestException(String.format("Cannot add new parking. Parking with id %s already exists", parking.getId()));
            }

            Address address = addressService.add(parking.getAddress()); // Use already existing address if it exists in db
            if (address != null) {
                parking.setAddress(address);
            }

            ParkingOwner parkingOwner = parkingOwnerService.add(parking.getParkingOwner(), true); // Use already existing parking owner if it exists in db
            if (parkingOwner != null) {
                parking.setParkingOwner(parkingOwner);
            }

            parking.setAddedDateTime(null);
            String result = String.valueOf(parkingService.add(parking).getId());

            return ResponseEntity.ok(result);
        }

        throw new UnauthorizedException("Request is unauthorized");
    }

    @PutMapping(path = "/p/parkings/{parkingId}")
    public ResponseEntity<Parking> putParkingById(
        @RequestHeader HttpHeaders headers, 
        @PathVariable Long parkingId, 
        @RequestBody Parking updatedParking,
        @RequestParam(required = true) boolean createNewOwner //If true then new owner entry will be created and parking will point to it. If false then data of current owner will be modified
    ){
        if (securityService.isAuthorized(headers)) {

            Address address = addressService.add(updatedParking.getAddress()); // Use already existing address if it exists in db
            if (address != null) {
                updatedParking.setAddress(address);
            }

            if (createNewOwner) // Create new parking owner for this parking and leave original one intact
            {
                ParkingOwner parkingOwner = parkingOwnerService.add(updatedParking.getParkingOwner(), false);
                if(parkingOwner == null) {
                    throw new InvalidRequestException(String.format("Cannot create new parking owner. Company with name %s already exists", updatedParking.getParkingOwner().getCompanyName()));
                }
                updatedParking.setParkingOwner(parkingOwner);
            }
            else // Use already existing parking owner if it exists in db, if no then create new one
            {
                ParkingOwner parkingOwner = parkingOwnerService.add(updatedParking.getParkingOwner(), true); 
                if (parkingOwner != null) {
                    updatedParking.setParkingOwner(parkingOwner);
                }
            }
            
            Parking parking = parkingService.updateById(parkingId, updatedParking);
            
            if (parking != null) {
                return ResponseEntity.ok(parking) ;
            }
            else {
                throw new InvalidRequestException(String.format("Parking with id %s does not exists", parkingId));
            }
        }
        throw new UnauthorizedException("Request is unauthorized");
    }


    @GetMapping(path = "/p/parkings")
    public ResponseEntity<PageDTO<ParkingDTO>> getAllParkings(
        @RequestHeader HttpHeaders headers, 

        @RequestParam(required = false) Long id,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Integer minimumSpotsTotal,
        @RequestParam(required = false) String companyName,
        @RequestParam(required = false) String country,
        @RequestParam(required = false) String town,
        @RequestParam(required = false) String streetName,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime,

        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int pageSize
    ){
        logHeaders(headers);
        if (securityService.isAuthorized(headers)) 
        {
                List<ParkingDTO> parkings = new ArrayList<ParkingDTO>();
                Pageable paging = PageRequest.of(page, pageSize);
                Page<Parking> pageParkings = parkingService.findAll(
                    id,
                    name,  
                    minimumSpotsTotal,
                    companyName,
                    country,
                    town,
                    streetName,
                    startDateTime,
                    endDateTime,
                    paging);

                for (Parking parking : pageParkings) {
                    parkings.add(new ParkingDTO(parking, 0f)); // Parking total price doesn't matter for parkly frontend (new ParkingDTO could be done, just for parkly)
                }
                
                PageDTO<ParkingDTO> parkingsPageDTO = new PageDTO<ParkingDTO>(pageParkings.getNumber(), parkings, pageParkings.getTotalElements(),pageParkings.getTotalPages());
                return ResponseEntity.ok(parkingsPageDTO);
        }
        throw new UnauthorizedException("Request is unauthorized");
    }

    @GetMapping(path = "/p/parkings/{parkingId}")
    public ResponseEntity<Parking> getParkingById(@RequestHeader HttpHeaders headers, @PathVariable Long parkingId) {
        logHeaders(headers);
        if (securityService.isAuthorized(headers)) {
            Parking parking = parkingService.findById(parkingId);
            return parking != null ? ResponseEntity.ok(parking) : ResponseEntity.badRequest().body(Parking.EMPTY);
        }
        throw new UnauthorizedException("Request is unauthorized");
    }


    @DeleteMapping(path = "/p/parkings/{parkingId}")
    public ResponseEntity<String> deleteParking(@RequestHeader HttpHeaders headers, @PathVariable Long parkingId) {
        logHeaders(headers);
        if (securityService.isAuthorized(headers)) {
            boolean deleted = parkingService.deleteById(parkingId);
            if (!deleted) {

                throw new InvalidRequestException(String.format("Parking with id %s does not exists", parkingId));
            }
            return ResponseEntity.ok(String.format("Parking with id %s deleted", parkingId));
        }
        throw new UnauthorizedException("Request is unauthorized");
    }


    // ---------- Bookly API ----------

    @GetMapping(path = "/b/parkings")
    public ResponseEntity<PageDTO<ParkingDTO>> getAllParkingsBookly(
        @RequestHeader HttpHeaders headers, 
        @RequestParam(required = true) String apiKey,

        @RequestParam(required = false) Long id,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Integer minimumSpotsTotal,
        @RequestParam(required = false) String companyName,
        @RequestParam(required = false) String country,
        @RequestParam(required = false) String town,
        @RequestParam(required = false) String streetName,
        @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
        @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime,

        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int pageSize
    ){
        if (securityService.isAuthorized(apiKey)) {// Bookly autorization is done using apiKey passed as parameter 
                List<ParkingDTO> parkings = new ArrayList<ParkingDTO>();
                Pageable paging = PageRequest.of(page, pageSize);
                Page<Parking> pageParkings = parkingService.findAll(
                    id,
                    name,  
                    minimumSpotsTotal,
                    companyName,
                    country,
                    town,
                    streetName,
                    startDateTime,
                    endDateTime,
                    paging);


                long hoursCount = startDateTime.until(endDateTime, ChronoUnit.HOURS);
                parkings = parkingService.createParkingDTOs(pageParkings.getContent(), hoursCount);

                PageDTO<ParkingDTO> parkingsPageDTO = new PageDTO<ParkingDTO>(pageParkings.getNumber(), parkings, pageParkings.getTotalElements(),pageParkings.getTotalPages());
                return ResponseEntity.ok(parkingsPageDTO);
        }
        throw new UnauthorizedException("Request is unauthorized");
    }

    @GetMapping(path = "/b/parkings/{parkingId}")
    public ResponseEntity<Parking> getParkingByIdBookly(  
        @RequestHeader HttpHeaders headers,
        @RequestParam(required = true) String apiKey, 
        @PathVariable Long parkingId
    ){
        if (securityService.isAuthorized(apiKey)) { // Bookly autorization is done using apiKey passed as parameter
            Parking parking = parkingService.findById(parkingId);
            return parking != null ? ResponseEntity.ok(parking) : ResponseEntity.badRequest().body(Parking.EMPTY);
        }
        throw new UnauthorizedException("Request is unauthorized");
    }


}

