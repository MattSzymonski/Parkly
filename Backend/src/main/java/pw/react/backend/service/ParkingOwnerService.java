package pw.react.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.react.backend.model.data.Address;
import pw.react.backend.dao.ParkingOwnerRepository;
import pw.react.backend.model.data.ParkingOwner;

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
    public ParkingOwner add(ParkingOwner parkingOwner, boolean reuseIfExists) {
        
        Address address = addressService.add(parkingOwner.getAddress()); // Find same address
        if (address != null) { 
            parkingOwner.setAddress(address); // If same address found then just save new address but with same id as old one (override)
        } 

        if (reuseIfExists) { // Check if company with this name exits, if true then do not crete new one
            repository.findByCompanyName(parkingOwner.getCompanyName()).ifPresent(parkingOwnerInTable -> parkingOwner.setId(parkingOwnerInTable.getId()));
        }
        else
        {
            if (repository.findByCompanyName(parkingOwner.getCompanyName()).isPresent()) { // Cannot create new owner with same name
                return null;
            }
        }

        return repository.save(parkingOwner);
    }
}