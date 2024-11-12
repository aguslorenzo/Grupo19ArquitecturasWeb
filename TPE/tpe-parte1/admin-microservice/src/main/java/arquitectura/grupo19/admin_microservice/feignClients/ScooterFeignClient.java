package arquitectura.grupo19.admin_microservice.feignClients;

import arquitectura.grupo19.admin_microservice.dto.ScooterDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "scooter-microservice")
public interface ScooterFeignClient {

    @GetMapping("/{id}")
    public ScooterDto getScooterById(@PathVariable Long id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveScooter(@RequestBody ScooterDto scooterDto);
	
}