package com.Purrrfect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.Purrrfect.Repo")
public class PurrrfectBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(PurrrfectBackendApplication.class, args);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String hash = encoder.encode("Rio2021@");
		System.out.println("Generated hash: " + hash);


	}
}
