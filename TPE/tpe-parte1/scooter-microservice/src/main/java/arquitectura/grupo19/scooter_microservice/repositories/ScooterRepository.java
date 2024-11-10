package arquitectura.grupo19.scooter_microservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import arquitectura.grupo19.scooter_microservice.entities.Scooter;

@Repository
public interface ScooterRepository extends JpaRepository<Scooter, Long> {
}
