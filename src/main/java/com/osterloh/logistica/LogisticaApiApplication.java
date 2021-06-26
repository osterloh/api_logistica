package com.osterloh.logistica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LogisticaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogisticaApiApplication.class, args);
	}

}
