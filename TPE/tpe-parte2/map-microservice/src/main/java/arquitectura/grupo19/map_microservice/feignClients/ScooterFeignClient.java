package arquitectura.grupo19.map_microservice.feignClients;

import arquitectura.grupo19.map_microservice.dto.ScooterDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "scooter-microservice", url="http://localhost:8087/scooters")
public interface ScooterFeignClient {

    /**
     * Para recibir las actualizaciones de ubicación y estado
     * de los monopatines.
     */
    @GetMapping("/scooters/{id}")
    ScooterDto getScooterById(@PathVariable("id") Long id);

    @GetMapping("scooters/latitud/{latitude}/longitud/{longitude}/radio/{radio}")
    List<ScooterDto> getScootersByLocation(@PathVariable("latitude") double latitude,
                                           @PathVariable("longitude") double longitude,
                                           @PathVariable("radio") double radio);
}