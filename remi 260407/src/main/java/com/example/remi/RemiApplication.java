package com.example.remi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RemiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RemiApplication.class, args);
		System.out.println("-- remi start --");
	}

}
