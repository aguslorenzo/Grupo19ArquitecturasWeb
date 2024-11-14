package arquitectura.grupo19.scooter_microservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "trip-microservice", url="http://localhost:8082/trips")
public interface TripFeignClient {

    @PutMapping("/{tripId}/updateamount")
    void updateTripWithAdditionalCharge(@PathVariable("tripId") Long tripId);

    @GetMapping("/scooters-by-trips")
    List<Long> getScootersWithMinTrips(@RequestParam("year") int year, @RequestParam("minTrips") int minTrips);
}
