package pw.react.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.client.RestTemplate;


import pw.react.backend.model.bookly.BooklyUser;

public class HttpService implements HttpClient {

    private final Logger logger = LoggerFactory.getLogger(HttpService.class);

    private final RestTemplate restTemplate;

    public HttpService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public BooklyUser getUserData(String endpointAddress, long userId, String apiKey) {
        String address = endpointAddress + "/" + userId + "?apiKey=" + apiKey;
        BooklyUser booklyUser = restTemplate.getForObject(address, BooklyUser.class);
        return booklyUser;
    }
}
