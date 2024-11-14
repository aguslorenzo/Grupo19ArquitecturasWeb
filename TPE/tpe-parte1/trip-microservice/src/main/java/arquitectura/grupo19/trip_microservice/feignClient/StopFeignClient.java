package arquitectura.grupo19.trip_microservice.feignClient;

import arquitectura.grupo19.trip_microservice.model.Stop;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "stop-microservice", url="http://localhost:8086/stops")
public interface StopFeignClient {

    @GetMapping
    List<Stop> getAllStops();

}
