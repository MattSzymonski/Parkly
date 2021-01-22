package pw.react.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pw.react.backend.appException.InvalidFileException;
import pw.react.backend.appException.ResourceNotFoundException;
import pw.react.backend.dao.AddressRepository;
import pw.react.backend.model.Address;

import java.io.IOException;

@Service
public class AddressService implements AddressMainService {

    private final Logger logger = LoggerFactory.getLogger(AddressService.class);

    private final AddressRepository repository;

    @Autowired
    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

}
