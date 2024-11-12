package arquitectura.grupo19.scooter_microservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "trip-microservice", url="http://localhost:8082/trips")
public interface TripFeignClient {

    @PutMapping("/{tripId}/updateamount")
    void updateTripWithAdditionalCharge(@PathVariable("tripId") Long tripId);

}
