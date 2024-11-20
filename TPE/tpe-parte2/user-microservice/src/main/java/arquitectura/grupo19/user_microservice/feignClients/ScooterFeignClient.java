package arquitectura.grupo19.user_microservice.feignClients;

import arquitectura.grupo19.user_microservice.dto.ScooterDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("SCOOTER-MICROSERVICE")
public interface ScooterFeignClient {
    @PostMapping("scooters/{scooterId}/start-scooter")
    void activateScooter(@PathVariable("scooterId") Long id);

    /*@PostMapping("/start-scooter/{userId}/{scooterId}")
    void activateScooter(@PathVariable("userId") Long userId, @PathVariable("scooterId") Long scooterId);*/
}
