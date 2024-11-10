package arquitectura.grupo19.stop_microservice.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import arquitectura.grupo19.stop_microservice.model.Scooter;

@FeignClient(name = "scooter-microservice")
public interface ScooterFeignClient {
	@GetMapping("/api/scooters/{id}")
	Scooter getScooterById(@PathVariable("id") long id);
}