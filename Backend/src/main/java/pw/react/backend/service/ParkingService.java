package pw.react.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.react.backend.dao.ParkingRepository;
import pw.react.backend.model.Parking;
import java.util.Optional;

@Service
public class ParkingService implements ParkingMainService {
    private final Logger logger = LoggerFactory.getLogger(ParkingMainService.class);

    private ParkingRepository repository;

    ParkingService() { /*Needed only for initializing spy in unit tests*/}

    @Autowired
    ParkingService(ParkingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Parking updateParking(Long id, Parking updatedCompany) {
        Parking result = Parking.EMPTY;
        if (repository.existsById(id)) {
            updatedCompany.setId(id);
            result = repository.save(updatedCompany);
            logger.info("Company with id {} updated.", id);
        }
        return result;
    }

    @Override
    public boolean deleteParking(Long companyId) {
        boolean result = false;
        if (repository.existsById(companyId)) {
            repository.deleteById(companyId);
            logger.info("Company with id {} deleted.", companyId);
            result = true;
        }
        return result;
    }

    @Override
    public Optional<Parking> findById(long parkingId) {
        return repository.findById(parkingId);
    }
}
