package arquitectura.grupo19.api_gateway.repositories;

import arquitectura.grupo19.api_gateway.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
        FROM User u JOIN FETCH u.authorities
        WHERE lower(u.username) = ?1
    """)
    Optional<User> findOneWithAuthoritiesByUsernameIgnoreCase(String username );
}
