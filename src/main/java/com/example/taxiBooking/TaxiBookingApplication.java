package com.example.taxiBooking;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TaxiBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxiBookingApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
				.setFieldMatchingEnabled(true)
				.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
		return mapper;
	}
//	@Bean
//	public OpenAPI openAPI() {
//		return new OpenAPI()
//				.info(
//						new Info()
//								.title("TaxiBookingApplication")
//								.description("Agile Method")
//								.version("1.0"));
//	}
}
