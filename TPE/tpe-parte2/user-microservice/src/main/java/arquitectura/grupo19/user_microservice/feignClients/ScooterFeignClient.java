package arquitectura.grupo19.user_microservice.feignClients;

import arquitectura.grupo19.user_microservice.dto.ScooterDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("SCOOTER-MICROSERVICE")
public interface ScooterFeignClient {
    @PostMapping("scooters/{scooterId}/start-scooter")
    void activateScooter(@PathVariable("scooterId") Long id);

    @PutMapping("scooters/{id}/stop")
    void stopScooter(@PathVariable Long id);
    @PutMapping("scooters/{id}/pause")
    ScooterDto pauseScooter(@PathVariable Long id);
    @PutMapping("scooters/{id}/restart")
    ScooterDto restartScooter(@PathVariable Long id);

}
