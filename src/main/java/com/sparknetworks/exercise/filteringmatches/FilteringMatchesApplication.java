package com.sparknetworks.exercise.filteringmatches;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FilteringMatchesApplication {
	private static final String[] ALLOWED_ORIGINS = new String[] {
			"http://web",
			"http://localhost:4200"
	};

	public static void main(String[] args) {
		SpringApplication.run(FilteringMatchesApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/matches").allowedOrigins(ALLOWED_ORIGINS);
			}
		};
	}
}
