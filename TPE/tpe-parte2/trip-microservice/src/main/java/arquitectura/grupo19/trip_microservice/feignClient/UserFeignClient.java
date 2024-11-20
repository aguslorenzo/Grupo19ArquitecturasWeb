package arquitectura.grupo19.trip_microservice.feignClient;

import arquitectura.grupo19.trip_microservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("USER-MICROSERVICE")
public interface UserFeignClient {

    @GetMapping("users/{id}")
    User getUserById(@PathVariable long id);

    @GetMapping("users/{id}/minbalance/{cost}")
    boolean hasSufficientBalance(@PathVariable long id,@RequestParam double cost);
    @PostMapping("users/{id}/deduct/{cost}")
    void deductBalance(@PathVariable long id,@RequestParam double cost);

    @PostMapping("users/{id}/notify/")
    void notifyUser(@PathVariable long id, @RequestBody String message);

}
