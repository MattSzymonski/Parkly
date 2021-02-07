package pw.react.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.react.backend.dao.AddressRepository;
import pw.react.backend.model.data.Address;


@Service
public class AddressService implements AddressMainService {

    private final AddressRepository repository;

    @Autowired
    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public Address add(Address address) {  
        repository.findByCountryAndTownAndStreetNameAndStreetNumber(address.getCountry(), address.getTown(), address.getStreetName(), address.getStreetNumber()).ifPresent(addressInTable -> address.setId(addressInTable.getId())); 
        return repository.save(address);
    }
}
