package arquitectura.grupo19.user_microservice.feignClients;

import arquitectura.grupo19.user_microservice.dto.TripResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("TRIP-MICROSERVICE")
public interface TripFeignClient {
    @PostMapping("trips/{userId}/{scooterId}")
    TripResponseDto createTrip(@PathVariable("userId") Long userId, @PathVariable("scooterId") Long scooterId);

}
