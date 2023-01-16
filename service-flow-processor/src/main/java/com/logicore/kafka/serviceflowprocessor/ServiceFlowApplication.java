package com.logicore.kafka.serviceflowprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ServiceFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceFlowApplication.class, args);
	}

}
