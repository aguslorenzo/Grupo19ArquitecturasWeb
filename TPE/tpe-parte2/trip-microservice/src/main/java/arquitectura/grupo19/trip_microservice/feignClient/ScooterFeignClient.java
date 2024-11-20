package arquitectura.grupo19.trip_microservice.feignClient;

import arquitectura.grupo19.trip_microservice.model.Scooter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "scooter-microservice", url="http://localhost:8087/scooters")
public interface ScooterFeignClient {

    @GetMapping("/{id}")
    Scooter getScooterById(@PathVariable long id);

    @PatchMapping("/{id}/stop")
    void deactivateScooter(@PathVariable long id);

    @GetMapping("/{id}/available")
    boolean isAvailable(@PathVariable long id);

    @PostMapping("/{id}/triptime/{time}")
    void addTimeOfUse(@PathVariable long id, @PathVariable int time);

}
