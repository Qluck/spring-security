package ua.nure.kulakov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ua.nure.kulakov")
public class KulakovApplication {

	public static void main(String[] args) {
		SpringApplication.run(KulakovApplication.class, args);
	}

}
