package arquitectura.grupo19.admin_microservice.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "trip-microservice", url="http://localhost:8082/trips")
public interface TripFeignClient {

    @GetMapping("/total-billed")
    double getTotalBilledInPeriod(@RequestParam("year") int year,
                                  @RequestParam("startMonth") int startMonth,
                                  @RequestParam("endMonth") int endMonth);
}
