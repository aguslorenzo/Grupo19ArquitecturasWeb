package arquitectura.grupo19.admin_microservice.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("USER-MICROSERVICE")
public interface UserFeignClient {

    @PutMapping("users/{id}/toggle-status")
    void toggleAccountStatus(@PathVariable("id") long id);
}
