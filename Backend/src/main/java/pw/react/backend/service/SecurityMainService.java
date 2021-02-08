package pw.react.backend.service;
import org.springframework.http.HttpHeaders;
import pw.react.backend.model.Credentials;

public interface SecurityMainService {
    boolean isAuthenticated(HttpHeaders headers);
    boolean isAuthorized(HttpHeaders headers);
    boolean isAuthenticated(String apiKey);
    boolean isAuthorized(String apiKey);
    String login(Credentials credentials);
}
