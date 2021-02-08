package pw.react.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import pw.react.backend.appException.InvalidRequestException;
import pw.react.backend.model.bookly.BooklyUser;

//@RestController
public class HttpService implements HttpClient {

    private final Logger logger = LoggerFactory.getLogger(HttpService.class);

    private final RestTemplate restTemplate;

    public HttpService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public BooklyUser getUserData(String endpointAddress, long userId, String apiKey) {

        String address = endpointAddress + "/" + userId; //+ "?apiKey=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Security-Token", apiKey);
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<BooklyUser> response = restTemplate.exchange(address, HttpMethod.GET, request, BooklyUser.class,1);
        
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new InvalidRequestException("Request failed");
        } 

        //BooklyUser booklyUser = restTemplate.getForObject(address, BooklyUser.class);
        //return booklyUser;
    }
}
