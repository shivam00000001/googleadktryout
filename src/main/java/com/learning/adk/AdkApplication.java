package com.learning.adk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AdkApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdkApplication.class, args);
	}

}
