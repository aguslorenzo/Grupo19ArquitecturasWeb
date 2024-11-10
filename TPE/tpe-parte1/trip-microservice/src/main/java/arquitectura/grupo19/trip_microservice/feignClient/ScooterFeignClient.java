package arquitectura.grupo19.trip_microservice.feignClient;

import arquitectura.grupo19.trip_microservice.model.Scooter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "scooter-microservice")
public interface ScooterFeignClient {

    @GetMapping("/api/scooters/scooter/{id}")
    Scooter getScooterById(@PathVariable long id);

    @PatchMapping("/api/scooters/scooter/{id}/deactivate")
    void deactivateScooter(@PathVariable long id);
}
