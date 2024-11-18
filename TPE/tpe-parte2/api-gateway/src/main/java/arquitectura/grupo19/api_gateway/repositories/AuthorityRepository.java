package arquitectura.grupo19.api_gateway.repositories;

import arquitectura.grupo19.api_gateway.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {

}
