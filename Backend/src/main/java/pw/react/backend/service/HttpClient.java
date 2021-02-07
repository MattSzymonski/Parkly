package pw.react.backend.service;

import pw.react.backend.model.bookly.BooklyUser;

public interface HttpClient {
    BooklyUser getUserData(String endpointAddress, long userId, String apiKey);
}
