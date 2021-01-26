package pw.react.backend.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pw.react.backend.appException.InvalidFileException;
import pw.react.backend.appException.ResourceNotFoundException;
import pw.react.backend.dao.AddressRepository;
import pw.react.backend.dao.BookingRepository;
import pw.react.backend.model.Address;
import pw.react.backend.model.Booking;
import pw.react.backend.model.bookly.BooklyBooking;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public interface BooklyBooking1 {
    Long getBooklyUserId();
    Long getGetParkingId();
    LocalDateTime getStartDateTime();
    LocalDateTime getEndDateTime();
}
