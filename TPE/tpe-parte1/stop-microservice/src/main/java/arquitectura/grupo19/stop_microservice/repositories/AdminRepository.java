package arquitectura.grupo19.stop_microservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import arquitectura.grupo19.stop_microservice.entities.Stop;

@Repository
public interface AdminRepository extends JpaRepository<Stop, Long> {
}
