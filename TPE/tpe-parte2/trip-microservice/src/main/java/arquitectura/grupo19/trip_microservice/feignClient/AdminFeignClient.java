package arquitectura.grupo19.trip_microservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "admin-microservice", url = "http://localhost:8085/admins")
public interface AdminFeignClient {

    @GetMapping("/cost")
    double getCostTrip();

    @GetMapping("/cost-with-surcharge")
    double getCostTripWithSurcharge();
}
