package arquitectura.grupo19.map_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MapMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MapMicroserviceApplication.class, args);
	}

}
