package arquitectura.grupo19.report_microservice.feignClient;

import arquitectura.grupo19.report_microservice.models.Scooter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "scooter-microservice")
public interface ScooterFeignClient {

    @GetMapping("/scooters/{id}/kilometers")
    Double getKilometers(@PathVariable long id);

    @GetMapping("/scooters/{id}")
    Scooter getScooterById(@PathVariable Long id);

    @GetMapping("/scooters/{id}/usageTime")
    int getUsageTime(@PathVariable long id);

    @GetMapping("/scooters/{id}/activeTime")
    int getActiveTime(@PathVariable long id);
}
