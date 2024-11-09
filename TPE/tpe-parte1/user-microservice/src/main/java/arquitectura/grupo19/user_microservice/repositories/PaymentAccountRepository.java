package arquitectura.grupo19.user_microservice.repositories;

import arquitectura.grupo19.user_microservice.entities.PaymentAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentAccountRepository extends JpaRepository<PaymentAccount, Long> {
}
