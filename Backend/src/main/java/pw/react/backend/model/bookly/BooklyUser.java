package pw.react.backend.model.bookly;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@JsonIgnoreProperties(ignoreUnknown = true)
public class BooklyUser { // Reflects user class in Bookly system (TO BE CHANGED WHEN DATA CONTRACT WITH BOOKLY WILL BE NEGOTIATED)

    @ApiModelProperty(position = 1)
    @Getter @Setter private long id;

    @ApiModelProperty(position = 2)
    @Getter @Setter private String firstName;

    @ApiModelProperty(position = 3)
    @Getter @Setter private String lastName;

    @ApiModelProperty(position = 4)
    @Getter @Setter private String phoneNumber;

    @ApiModelProperty(position = 5)
    @Getter @Setter private String emailAddress;

    @ApiModelProperty(position = 6)
    @Getter @Setter private String registrationPlates;
}
