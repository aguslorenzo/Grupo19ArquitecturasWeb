package arquitectura.grupo19.map_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MapMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MapMicroserviceApplication.class, args);
	}

}
