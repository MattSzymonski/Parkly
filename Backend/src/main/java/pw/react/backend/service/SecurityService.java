package pw.react.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import pw.react.backend.dao.ParklyUserRepository;
import pw.react.backend.model.Credentials;
import pw.react.backend.model.data.ParklyUser;
import java.util.Optional;



@Service
public class SecurityService implements SecurityMainService {

    private final ParklyUserRepository repository;
    private static final String SECURITY_TOKEN = "security-token";
    //private final String SECURITY_HEADER_VALUE = "secureMe";

    @Autowired
    public SecurityService(ParklyUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isAuthenticated(HttpHeaders headers) {
        if (headers == null) {
            return false;
        }
        return repository.findBySecurityToken(headers.getFirst(SECURITY_TOKEN)).isPresent();
    }

    @Override
    public boolean isAuthorized(HttpHeaders headers) {
        return isAuthenticated(headers);
    }

    @Override
    public String login(Credentials credentials) {
        if (credentials == null) {
            return null;
        }
        
        Optional<ParklyUser> parklyUser = repository.findByLoginAndPassword(credentials.getLogin(), credentials.getPassword());

        return parklyUser.isPresent() ? parklyUser.get().getSecurityToken() : null;  // Find user with these credentials in db and return its security token
    }
}
