package arquitectura.grupo19.trip_microservice.feignClient;

import arquitectura.grupo19.trip_microservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("USER-MICROSERVICE")
public interface UserFeignClient {

    @GetMapping("users/{id}")
    User getUserById(@PathVariable long id);

    @GetMapping("users/{id}/minbalance/{cost}") //TODO repensar si esto es un path variable o query param
    boolean hasSufficientBalance(@PathVariable long id,@PathVariable double cost);

    @PostMapping("users/{id}/deduct/{cost}") //TODO repensar si esto es un path variable o query param
    void deductBalance(@PathVariable long id,@PathVariable double cost);

    @PostMapping("users/{id}/notify/")
    void notifyUser(@PathVariable long id, @RequestBody String message);

}
