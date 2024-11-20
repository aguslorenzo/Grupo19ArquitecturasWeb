package arquitectura.grupo19.user_microservice.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "scooter-microservice", url="http://localhost:8087/scooters")
public interface ScooterFeignClient {
    @PostMapping("/{scooterId}/start-scooter")
    void activateScooter(@PathVariable("scooterId") Long id);

    @PutMapping("/{id}/stop")
    void stopScooter(@PathVariable Long id);

}
