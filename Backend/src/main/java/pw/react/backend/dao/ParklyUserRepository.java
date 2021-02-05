package pw.react.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.react.backend.model.data.ParklyUser;
import java.util.Optional;


public interface ParklyUserRepository extends JpaRepository<ParklyUser, Long> {
    Optional<ParklyUser> findByLoginAndPassword(String login, String password);
    Optional<ParklyUser> findBySecurityToken(String securityToken);
}
