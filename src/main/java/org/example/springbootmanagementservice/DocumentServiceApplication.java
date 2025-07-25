package org.example.springbootmanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.example.springbootmanagementservice")
public class DocumentServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DocumentServiceApplication.class, args);
	}
}
