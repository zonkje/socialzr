package com.szymek.socializr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SocializrApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocializrApplication.class, args);
	}

}
