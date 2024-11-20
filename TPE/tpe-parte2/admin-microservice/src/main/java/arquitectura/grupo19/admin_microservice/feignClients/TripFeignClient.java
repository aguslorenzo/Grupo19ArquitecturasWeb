package arquitectura.grupo19.admin_microservice.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("TRIP-MICROSERVICE")
public interface TripFeignClient {

    @GetMapping("trips/total-billed")
    double getTotalBilledInPeriod(@RequestParam("year") int year,
                                  @RequestParam("startMonth") int startMonth,
                                  @RequestParam("endMonth") int endMonth);
}
