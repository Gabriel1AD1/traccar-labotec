package com.labotec.traccar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TraccarApplication {

	public static void main(String[] args) {
		SpringApplication.run(TraccarApplication.class, args);
	}

}
