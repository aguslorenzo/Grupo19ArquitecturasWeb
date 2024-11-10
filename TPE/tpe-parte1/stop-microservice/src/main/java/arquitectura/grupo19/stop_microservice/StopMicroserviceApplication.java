package arquitectura.grupo19.stop_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class StopMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StopMicroserviceApplication.class, args);
	}

}
