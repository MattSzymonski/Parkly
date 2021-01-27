package pw.react.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pw.react.backend.appException.UnauthorizedException;
import pw.react.backend.dao.ParkingRepository;
import pw.react.backend.dao.ParkingOwnerRepository;
import pw.react.backend.model.ParkingDTO;
import pw.react.backend.model.data.Address;
import pw.react.backend.model.data.Parking;
import pw.react.backend.model.data.ParkingOwner;
import pw.react.backend.service.*;
import pw.react.backend.web.UploadFileResponse;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import static java.util.stream.Collectors.joining;



@RestController
@RequestMapping(path = "/parkings")
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

    @PostMapping(path = "")
    public ResponseEntity<String> createParkings(@RequestHeader HttpHeaders headers, @RequestBody Parking parking) { // In arguments are things that will be required to send in post request
        logHeaders(headers);
        if (securityService.isAuthorized(headers)) {

            Address address = addressService.addAddress(parking.getAddress()); // Use already existing address if it exists in db
            if (address != null) {
                parking.setAddress(address);
            }

            ParkingOwner parkingOwner = parkingOwnerService.addParkingOwner(parking.getParkingOwner()); // Use already existing parking owner if it exists in db
            if (parkingOwner != null) {
                parking.setParkingOwner(parkingOwner);
            }

            String result = String.valueOf(parkingService.addParking(parking).getId());

            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Request is unauthorized");
    }


    @GetMapping(path = "")
    public ResponseEntity<Map<String, Object>> getAllParkings(
        @RequestHeader HttpHeaders headers, 
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Integer spotsTotal, 
        @RequestParam(required = false) String companyName, 
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int pageSize
    ){
        logHeaders(headers);
        if (securityService.isAuthorized(headers)) 
        {
                List<ParkingDTO> parkings = new ArrayList<ParkingDTO>();
                Pageable paging = PageRequest.of(page, pageSize);
                Map<String, Object> response = new HashMap<>();
                Page<Parking> pageParkings = parkingService.findAll(
                    name, 
                    spotsTotal, 
                    companyName,
                    paging);

                for (Parking parking : pageParkings) {
                    parkings.add(ParkingDTO.CreateParkingDTO(parking));
                }
                
                response.put("parkings", parkings);
                response.put("currentPage", pageParkings.getNumber());
                response.put("totalItems", pageParkings.getTotalElements());
                response.put("totalPages", pageParkings.getTotalPages());
        
                return new ResponseEntity<>(response, HttpStatus.OK);
        }
        throw new UnauthorizedException("Request is unauthorized");
    }


    @GetMapping(path = "/{parkingId}")
    public ResponseEntity<Parking> getParkingById(@RequestHeader HttpHeaders headers, @PathVariable Long parkingId) {
        logHeaders(headers);
        if (securityService.isAuthorized(headers)) {
            Parking parking = parkingService.findById(parkingId);
            return parking != null ? ResponseEntity.ok(parking) : ResponseEntity.badRequest().body(Parking.EMPTY);
            //return parking != null ? ResponseEntity.ok(parking) : ResponseEntity.badRequest().body(String.format("Parking with id %s does not exists", parkingId));
            //return ResponseEntity.ok(repository.findById(parkingId).orElseGet(() -> Parking.EMPTY));
            //return ResponseEntity.ok(parkingService.findById(parkingId));
        }
        throw new UnauthorizedException("Request is unauthorized");
    }

    @DeleteMapping(path = "/{parkingId}")
    public ResponseEntity<String> deleteParking(@RequestHeader HttpHeaders headers, @PathVariable Long parkingId) {
        logHeaders(headers);
        if (securityService.isAuthorized(headers)) {
            boolean deleted = parkingService.deleteParkingById(parkingId);
            if (!deleted) {
                return ResponseEntity.badRequest().body(String.format("Parking with id %s does not exists", parkingId));
            }
            return ResponseEntity.ok(String.format("Parking with id %s deleted", parkingId));
        }
        throw new UnauthorizedException("Request is unauthorized");
    }
}

