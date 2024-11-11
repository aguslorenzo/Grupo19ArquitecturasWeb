package arquitectura.grupo19.scooter_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableFeignClients
public class ScooterMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScooterMicroserviceApplication.class, args);
	}

}
