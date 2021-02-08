package pw.react.backend.appException;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException(String message) {
        super(message);
    }
}
