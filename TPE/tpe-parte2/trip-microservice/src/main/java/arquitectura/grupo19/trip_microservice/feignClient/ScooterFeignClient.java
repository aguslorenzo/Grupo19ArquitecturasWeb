package arquitectura.grupo19.trip_microservice.feignClient;

import arquitectura.grupo19.trip_microservice.model.Scooter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient("SCOOTER-MICROSERVICE")
public interface ScooterFeignClient {

    @GetMapping("scooters/{id}")
    Scooter getScooterById(@PathVariable long id);

    @PatchMapping("scooters/{id}/stop")
    void deactivateScooter(@PathVariable long id);

    @GetMapping("scooters/{id}/available")
    boolean isAvailable(@PathVariable long id);
    @PutMapping("scooters/toggle-status/{id}")
    void toggleStatus(@PathVariable long id);

    @PostMapping("scooters/{id}/triptime/{time}")
    void addTimeOfUse(@PathVariable long id, @PathVariable int time);
}
