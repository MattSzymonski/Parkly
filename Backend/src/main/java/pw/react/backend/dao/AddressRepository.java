package pw.react.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.react.backend.model.data.Address;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByCountryAndTownAndStreetNameAndStreetNumber( String country,  String town, String streetName, String streetNumber);
}
