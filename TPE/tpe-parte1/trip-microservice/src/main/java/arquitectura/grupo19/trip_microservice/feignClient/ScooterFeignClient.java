package arquitectura.grupo19.trip_microservice.feignClient;

import arquitectura.grupo19.trip_microservice.model.Scooter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Duration;

@FeignClient(name = "scooter-microservice")
public interface ScooterFeignClient {

    @GetMapping("/scooters/id/{id}")
    Scooter getScooterById(@PathVariable long id);

    @PatchMapping("/scooters/id/{id}/stop")
    void deactivateScooter(@PathVariable long id);

    @GetMapping("/scooters/id/{id}/available")
    boolean isAvailable(@PathVariable long id);

    @PostMapping("/scooters/{id}/triptime/{time}")
    void addTimeOfUse(@PathVariable long id, @PathVariable Duration time);
}
