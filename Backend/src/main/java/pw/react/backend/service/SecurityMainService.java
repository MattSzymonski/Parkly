package pw.react.backend.service;

import org.springframework.http.HttpHeaders;

import pw.react.backend.model.Credentials;

public interface SecurityMainService {
    boolean isAuthenticated(HttpHeaders headers);
    boolean isAuthorized(HttpHeaders headers);
    String login(Credentials credentials);
}
