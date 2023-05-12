package com.example.greenstitchauthentication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
//@OpenAPIDefinition(info = @Info(title = "GreenStitch REST APIs", version = "1.0", description = "GreenStitch REST APIs", contact = @Contact(name = "Ratnesh Mishra")), security = {@SecurityRequirement(name = "bearerToken")})
public class GreenStitchAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreenStitchAuthenticationApplication.class, args);
    }

}
