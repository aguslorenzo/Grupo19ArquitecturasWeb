package arquitectura.grupo19.admin_microservice.feignClients;

import arquitectura.grupo19.admin_microservice.dto.ScooterDto;
import arquitectura.grupo19.admin_microservice.dto.StopDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "stop-microservice", url="http://localhost:8081/stops")
public interface StopFeignClient {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void saveStop(@RequestBody StopDto stopDto);

    @DeleteMapping("/stops/{id}")
    StopDto deleteStop(@PathVariable long id);

}
