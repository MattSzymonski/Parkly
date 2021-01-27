package pw.react.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pw.react.backend.appException.UnauthorizedException;
import pw.react.backend.dao.ParkingRepository;
import pw.react.backend.dao.ParkingOwnerRepository;
import pw.react.backend.model.data.Address;
import pw.react.backend.model.data.Parking;
import pw.react.backend.model.data.ParkingOwner;
import pw.react.backend.service.*;
import pw.react.backend.web.UploadFileResponse;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import pw.react.backend.model.Credentials;
import static java.util.stream.Collectors.joining;





@RestController
@RequestMapping(path = "/login")
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(ParkingController.class);
    private final SecurityService securityService;


    @Autowired
    public LoginController(SecurityService securityService) {
        this.securityService = securityService;
    }

    private void logHeaders(@RequestHeader HttpHeaders headers) {
        logger.info("Controller request headers {}",
                headers.entrySet()
                        .stream()
                        .map(entry -> String.format("%s->[%s]", entry.getKey(), String.join(",", entry.getValue())))
                        .collect(joining(","))
        );
    }

    @PostMapping(path = "") // For Flatly frontend
    public ResponseEntity<String> login(@RequestHeader HttpHeaders headers, @RequestBody Credentials credentials) { 
        logHeaders(headers);

        String securityToken = securityService.login(credentials);
        if (securityToken != null) {
            return ResponseEntity.ok(securityToken);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong login or password");
    }

}
