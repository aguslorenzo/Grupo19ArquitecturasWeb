package arquitectura.grupo19.api_gateway.repositories;

import arquitectura.grupo19.api_gateway.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    @Query("SELECT u FROM AuthUser u WHERE u.username = :username")
    AuthUser findUserEntityByUsername(@Param("username") String username);
}
