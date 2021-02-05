package pw.react.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pw.react.backend.dao.ParkingRepository;
import pw.react.backend.model.data.Parking;
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
    public Page<Parking> findAll(
        String name,
        String companyName,
        Pageable pageable
    ) {
        return repository.findAll(
            name, 
            companyName,
            pageable);
    }

    @Override
    public Parking findById(long parkingId) {
        return repository.findById(parkingId).orElseGet(() -> null);
    }

    @Override
    public Parking updateParkingById(Long parkingId, Parking updatedParking) {
        Parking result = Parking.EMPTY;
        if (repository.existsById(parkingId)) {
            updatedParking.setId(parkingId);
            result = repository.save(updatedParking);
        }
        return result;
    }

    @Override
    public boolean deleteParkingById(Long parkingId) {
        boolean result = false;
        if (repository.existsById(parkingId)) {
            repository.deleteById(parkingId);
            result = true;
        }
        return result;
    }

	public Parking addParking(Parking parking) {
		return repository.save(parking);
	}
}
