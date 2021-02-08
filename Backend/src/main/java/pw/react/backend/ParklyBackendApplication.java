package pw.react.backend;

import com.google.common.base.Predicates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
	public Docket swaggerWholeApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Parkly Whole API")
				.select()
				.paths(
					Predicates.or(
						PathSelectors.ant("/p/login/**"), 
						PathSelectors.ant("/p/bookings/**"), 
						PathSelectors.ant("/b/bookings/**"), 
						PathSelectors.ant("/p/parkings/**"), 
						PathSelectors.ant("/b/parkings/**")
					)
				)
				.build().apiInfo(createApiInfo());//.ignoredParameterTypes(Address.class, BooklyBooking.class, Parking.class, ParkingOwner.class);
	}

	@Bean
	public Docket swaggerExternalApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Parkly External API")
				.select()
				.paths(
					Predicates.or(
						PathSelectors.ant("/b/bookings/**"), 
						PathSelectors.ant("/b/parkings/**")
					)
				)
				.build().apiInfo(createApiInfo());//.ignoredParameterTypes(Address.class, BooklyBooking.class, Parking.class, ParkingOwner.class);
	}

	@Bean
	public Docket swaggerInternalApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Parkly Internal API")
				.select()
				.paths(
					Predicates.or(
						PathSelectors.ant("/p/login/**"), 
						PathSelectors.ant("/p/bookings/**"), 
						PathSelectors.ant("/p/parkings/**")
					)
				)
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
