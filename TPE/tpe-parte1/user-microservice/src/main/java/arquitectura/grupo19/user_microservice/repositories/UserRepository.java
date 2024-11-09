package arquitectura.grupo19.user_microservice.repositories;

import arquitectura.grupo19.user_microservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
