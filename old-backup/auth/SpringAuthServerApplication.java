package com.project.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
public class SpringAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAuthServerApplication.class, args);
	}

}
