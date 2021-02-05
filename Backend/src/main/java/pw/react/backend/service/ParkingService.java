package pw.react.backend.service;

import java.util.Optional;

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
        Long id,
        String name,
        Integer minimumSpotsTotal,
        String companyName,
        String country,
        String town,
        String streetName,
        Pageable pageable
    ) {
        return repository.findAll(
            id,
            name, 
            minimumSpotsTotal,
            companyName,
            country,
            town,
            streetName,
            pageable);
    }

    @Override
    public Parking findById(long parkingId) {
        return repository.findById(parkingId).orElseGet(() -> null);
    }

    @Override
    public Parking updateById(Long parkingId, Parking updatedParking) {
        Parking result = null;
        Parking parkingToUpdate = repository.findById(parkingId).get();

        if (parkingToUpdate != null) {
            updatedParking.setId(parkingToUpdate.getId());
            updatedParking.setAddedDateTime(parkingToUpdate.getAddedDateTime());
            result = repository.save(updatedParking);
        }
        return result;
    }

    @Override
    public boolean deleteById(Long parkingId) {
        boolean result = false;
        if (repository.existsById(parkingId)) {
            repository.deleteById(parkingId);
            result = true;
        }
        return result;
    }

	public Parking add(Parking parking) {
		return repository.save(parking);
	}
}
