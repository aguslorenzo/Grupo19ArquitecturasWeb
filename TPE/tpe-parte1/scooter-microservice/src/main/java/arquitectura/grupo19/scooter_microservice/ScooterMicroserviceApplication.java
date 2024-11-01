package arquitectura.grupo19.scooter_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ScooterMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScooterMicroserviceApplication.class, args);
	}

}
