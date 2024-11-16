package arquitectura.grupo19.user_microservice.feignClients;

import arquitectura.grupo19.user_microservice.models.ScooterLocation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("MAP-MICROSERVICE")
public interface MapFeignClient {

    @GetMapping("/maps/scooters/nearby")
    List<ScooterLocation> findScootersNearby(@RequestParam("latitude") double latitude,
                                             @RequestParam("longitude") double longitude,
                                             @RequestParam("radius") double radius);
}