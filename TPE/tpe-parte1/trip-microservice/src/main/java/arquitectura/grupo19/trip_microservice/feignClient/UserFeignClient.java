package arquitectura.grupo19.trip_microservice.feignClient;

import arquitectura.grupo19.trip_microservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-microservice")
public interface UserFeignClient {

    @GetMapping("api/users/id/{id}")
    User getUserById(@PathVariable long id);

    @GetMapping("api/users/id/{id}/minbalance/{cost}")
    boolean hasSufficientBalance(long id, double cost);

    @PatchMapping("api/users/id/{id}/deduct/{cost}")
    void deductBalance(long id, double cost);

    @PatchMapping("api/users/id/{id}/notify/{message}")
    void notifyUser(long id, String message);

}
