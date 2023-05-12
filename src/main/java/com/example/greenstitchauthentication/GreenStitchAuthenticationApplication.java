package com.example.greenstitchauthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
//@OpenAPIDefinition(info = @Info(title = "GreenStitch REST APIs", version = "1.0", description = "GreenStitch REST APIs", contact = @Contact(name = "Ratnesh Mishra")), security = {@SecurityRequirement(name = "bearerToken")})
public class GreenStitchAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreenStitchAuthenticationApplication.class, args);
    }

}
