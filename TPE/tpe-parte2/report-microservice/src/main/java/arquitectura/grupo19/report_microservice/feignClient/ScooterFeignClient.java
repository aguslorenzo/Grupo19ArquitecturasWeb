package arquitectura.grupo19.report_microservice.feignClient;

import arquitectura.grupo19.report_microservice.models.Scooter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "scooter-microservice",url="http://localhost:8087/scooters")
public interface ScooterFeignClient {

    @GetMapping("/{id}/kilometers")
    Double getKilometers(@PathVariable long id);

    @GetMapping("/{id}")
    Scooter getScooterById(@PathVariable Long id);

    @GetMapping("/{id}/usageTime")
    int getUsageTime(@PathVariable long id);

    @GetMapping("/{id}/activeTime")
    int getActiveTime(@PathVariable long id);
}
