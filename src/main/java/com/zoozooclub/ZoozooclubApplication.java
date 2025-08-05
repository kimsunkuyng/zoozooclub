package com.zoozooclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}) 
@SpringBootApplication
public class ZoozooclubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZoozooclubApplication.class, args);
	}

}
