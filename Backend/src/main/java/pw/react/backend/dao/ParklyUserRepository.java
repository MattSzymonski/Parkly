package pw.react.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pw.react.backend.model.Booking;
import pw.react.backend.model.BooklyBooking1;
import pw.react.backend.model.ParklyUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface ParklyUserRepository extends JpaRepository<ParklyUser, Long> {
    Optional<ParklyUser> findByLoginAndPassword(String login, String password);
    Optional<ParklyUser> findBySecurityToken(String securityToken);

}
