package arquitectura.grupo19.trip_microservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "admin-microservice")
public interface AdminFeignClient {

    @GetMapping("/admins/cost")
    double getCostTrip();

    @GetMapping("/admins/costwithsurcharge")
    double getCostTripWithSurcharge();
}
