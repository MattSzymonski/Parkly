package pw.react.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pw.react.backend.model.bookly.BooklyBooking;
import pw.react.backend.model.data.Address;
import pw.react.backend.model.data.Parking;
import pw.react.backend.model.data.ParkingOwner;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ParklyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParklyBackendApplication.class, args);
	}
	
	@Bean
	public Docket get() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Parkly Internal API")
				.select()
				.paths(PathSelectors.regex("/parkings/*|/bookings/*"))
				.build().apiInfo(createApiInfo());//.ignoredParameterTypes(Address.class, BooklyBooking.class, Parking.class, ParkingOwner.class);
	}

	private ApiInfo createApiInfo() {

		return new ApiInfoBuilder().title("Parkly API")
                .description("")
                .termsOfServiceUrl(null)
                .license(null)
                .licenseUrl(null).version("1.0").build();
	}

	
}
