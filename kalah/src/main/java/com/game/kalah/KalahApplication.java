package com.game.kalah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class KalahApplication {
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(KalahApplication.class);
	}


	public static void main(String[] args) {
		SpringApplication.run(KalahApplication.class, args);
	}
}
