package arquitectura.grupo19.scooter_microservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "trip-microservice")
public interface TripFeignClient {

    @PutMapping("/trips/id/{tripId}/updateamount")
    void updateTripWithAdditionalCharge(@PathVariable("tripId") Long tripId);

}
