package com.cameriere.menu;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Menu microservice REST API Documentation.",
				description = "Cameriere Menu microservice REST API Documentation.",
				version = "v1",
				contact = @Contact(
						name = "Lucas Facci",
						email = "contact@lucasfacci.com",
						url = "https://lucasfacci.com"
				),
				license = @License(
						name = "MIT",
						url = "https://lucasfacci.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Cameriere System Menu microservice REST API Documentation.",
				url = "https://lucasfacci.com/swagger-ui.html"
		)
)
public class MenuApplication {

	public static void main(String[] args) {
		SpringApplication.run(MenuApplication.class, args);
	}

}
