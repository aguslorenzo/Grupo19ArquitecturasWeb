package arquitectura.grupo19.admin_microservice.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "user-microservice", url="http://localhost:8084/users")
public interface UserFeignClient {

    @PutMapping("/{id}/toggle-status")
    void toggleAccountStatus(@PathVariable("id") long id);
}
