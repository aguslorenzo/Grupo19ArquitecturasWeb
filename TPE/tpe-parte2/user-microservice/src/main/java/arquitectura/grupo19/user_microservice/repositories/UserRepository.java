package arquitectura.grupo19.user_microservice.repositories;

import arquitectura.grupo19.user_microservice.entities.PaymentAccount;
import arquitectura.grupo19.user_microservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT p FROM User u JOIN u.paymentAccounts p WHERE u.id = :userId AND p.balance >= :cost")
    List<PaymentAccount> findAccountsWithSufficientBalance(@Param("userId") long userId, @Param("cost") double cost);
}
