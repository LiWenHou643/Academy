package com.liwenhou.SchoolWebApp;

import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.liwenhou.SchoolWebApp.repository")
@EntityScan("com.liwenhou.SchoolWebApp.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class SchoolWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolWebApplication.class, args);
	}

}
