package pw.react.backend.model.bookly;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;


@JsonIgnoreProperties(ignoreUnknown = true)
public class BooklyUser { // Reflects user class in Bookly system (TO BE CHANGED WHEN DATA CONTRACT WITH BOOKLY WILL BE NEGOTIATED)

    @Getter @Setter private long id;
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private String phoneNumber;
    @Getter @Setter private String emailAddress;
    @Getter @Setter private String registrationPlates;
}
