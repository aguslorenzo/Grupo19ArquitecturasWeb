package arquitectura.grupo19.user_microservice.feignClients;

import arquitectura.grupo19.user_microservice.dto.TripResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "trip-microservice", url="http://localhost:8082/trips")
public interface TripFeignClient {

    @PostMapping("/{userId}/{scooterId}")
    TripResponseDto createTrip(@PathVariable("userId") Long userId, @PathVariable("scooterId") Long scooterId);

    @PutMapping("/endtrip/{tripId}/{scooterId}")
    ResponseEntity<?> endTrip(@PathVariable long tripId, @PathVariable long scooterId);

}
