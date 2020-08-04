package yte.etkinlikyonetim;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class EtkinlikYonetimApplication {

	public static void main(String[] args) {

		SpringApplication.run(EtkinlikYonetimApplication.class, args);
	}

}
