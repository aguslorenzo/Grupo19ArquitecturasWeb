package arquitectura.grupo19.admin_microservice.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-microservice", url="http://localhost:8084/users")
public interface UserFeignClient {

    @GetMapping("/users/{id}")
    void deleteUser(@PathVariable long id);

}
