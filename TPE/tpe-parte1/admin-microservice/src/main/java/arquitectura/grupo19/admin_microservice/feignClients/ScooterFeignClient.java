package arquitectura.grupo19.admin_microservice.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import arquitectura.grupo19.admin_microservice.model.Scooter;

@FeignClient(name = "scooter-microservice")
public interface ScooterFeignClient {
	
	@GetMapping("/api/scooters/id/{id}")
    Scooter getScooterById(@PathVariable long id);

    @PostMapping("/api/scooters")
    void addScooter(@RequestBody Scooter scooter);
	
}