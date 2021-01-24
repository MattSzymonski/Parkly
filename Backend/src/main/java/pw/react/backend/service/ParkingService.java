package pw.react.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pw.react.backend.dao.ParkingRepository;
import pw.react.backend.model.Parking;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Service
public class ParkingService implements ParkingMainService {
    private final Logger logger = LoggerFactory.getLogger(ParkingMainService.class);

    private ParkingRepository repository;

    ParkingService() {
        /* Needed only for initializing spy in unit tests */}

    @Autowired
    ParkingService(ParkingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Parking> findAll(String nameKeyword, Integer spotsTotalKeyword, Pageable pageable) {
        return repository.findAll(nameKeyword, spotsTotalKeyword, pageable);
    }

    @Override
    public Parking findById(long parkingId) {
        return repository.findById(parkingId).orElseGet(() -> Parking.EMPTY);

        //Optional<Parking> parking = repository.findById(parkingId);
        //return parking.isPresent() ? parking.get() : null;
    }

    @Override
    public Parking updateParkingById(Long parkingId, Parking updatedParking) {
        Parking result = Parking.EMPTY;
        if (repository.existsById(parkingId)) {
            updatedParking.setId(parkingId);
            result = repository.save(updatedParking);
            logger.info("Parking with id {} updated.", parkingId);
        }
        return result;
    }

    @Override
    public boolean deleteParkingById(Long parkingId) {
        boolean result = false;
        if (repository.existsById(parkingId)) {
            repository.deleteById(parkingId);
            logger.info("Parking with id {} deleted.", parkingId);
            result = true;
        }
        return result;
    }

	public Parking addParking(Parking parking) {
		return repository.save(parking);
	}
}
