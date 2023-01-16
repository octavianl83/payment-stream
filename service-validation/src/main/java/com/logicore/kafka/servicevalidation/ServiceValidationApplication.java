package com.logicore.kafka.servicevalidation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class ServiceValidationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceValidationApplication.class, args);
	}

}
