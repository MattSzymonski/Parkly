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
import pw.react.backend.model.data.Address;
import pw.react.backend.dao.ParkingOwnerRepository;
import pw.react.backend.model.data.ParkingOwner;

import java.io.IOException;

@Service
public class ParkingOwnerService implements ParkingOwnerMainService {

    private final ParkingOwnerRepository repository;
    private AddressService addressService;

    @Autowired
    public ParkingOwnerService(ParkingOwnerRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public ParkingOwner addParkingOwner(ParkingOwner parkingOwner) {
        
        Address address = addressService.addAddress(parkingOwner.getAddress()); // Find same address
        if (address != null) { 
            parkingOwner.setAddress(address); // If same address found then just save new address but with same id as old one (override)
        } 
        repository.findByCompanyName(parkingOwner.getCompanyName()).ifPresent(parkingOwnerInTable -> parkingOwner.setId(parkingOwnerInTable.getId()));

        return repository.save(parkingOwner);
    }
}