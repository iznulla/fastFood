package com.test.fastFood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class FastFoodApplication {
	public static void main(String[] args) {
		SpringApplication.run(FastFoodApplication.class, args);
	}

}
