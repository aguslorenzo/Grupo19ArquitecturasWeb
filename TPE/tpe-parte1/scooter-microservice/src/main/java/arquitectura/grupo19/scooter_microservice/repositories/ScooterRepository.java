package arquitectura.grupo19.scooter_microservice.repositories;

import arquitectura.grupo19.scooter_microservice.entities.ScooterState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import arquitectura.grupo19.scooter_microservice.entities.Scooter;

import java.util.List;

@Repository
public interface ScooterRepository extends JpaRepository<Scooter, Long> {

    // TODO implementar query
    @Query()
    Scooter findByIdAndIsActive(Long id, boolean isActive);

    // TODO implementar query
    // Encuentra todos los monopatines que están en estado PAUSADO
    @Query()
    List<Scooter> findAllByState(ScooterState state);

}
