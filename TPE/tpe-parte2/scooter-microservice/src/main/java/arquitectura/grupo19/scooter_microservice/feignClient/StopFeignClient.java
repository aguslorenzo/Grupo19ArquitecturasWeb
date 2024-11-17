package arquitectura.grupo19.scooter_microservice.feignClient;

import arquitectura.grupo19.scooter_microservice.dto.StopDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("STOP-MICROSERVICE")
public interface StopFeignClient {

    @GetMapping("stops")
    List<StopDto> getAllStops();
}
