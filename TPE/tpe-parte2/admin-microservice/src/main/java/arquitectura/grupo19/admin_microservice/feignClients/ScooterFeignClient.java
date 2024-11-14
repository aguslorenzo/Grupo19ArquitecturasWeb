package arquitectura.grupo19.admin_microservice.feignClients;

import arquitectura.grupo19.admin_microservice.dto.ScooterDto;
import arquitectura.grupo19.admin_microservice.dto.ScooterStatusCountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "scooter-microservice", url="http://localhost:8080/scooters")
public interface ScooterFeignClient {

    @GetMapping("/{id}")
    ScooterDto getScooterById(@PathVariable Long id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void saveScooter(@RequestBody ScooterDto scooterDto);

    @DeleteMapping("/{id}")
    ScooterDto deleteScooter(@PathVariable long id);

    @GetMapping("/trips")
    List<ScooterDto> getScootersWithTrips(@RequestParam("year") int year, @RequestParam("minTrips") int minTrips);

    @GetMapping("/status-count")
    ScooterStatusCountDto getScooterStatusCounts();
}