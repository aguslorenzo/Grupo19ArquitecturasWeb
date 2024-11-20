package arquitectura.grupo19.scooter_microservice.feignClient;

import arquitectura.grupo19.scooter_microservice.dto.ReportDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "report-microservice", url="http://localhost:8083/reports")
public interface ReportFeignClient {

    @GetMapping("/scooter/{scooterId}/usage-report")
    ReportDto getUsageReportByScooter(@PathVariable("scooterId") Long scooterId,
                                      @RequestParam("includePauseTimes") boolean includePauseTimes);
}
