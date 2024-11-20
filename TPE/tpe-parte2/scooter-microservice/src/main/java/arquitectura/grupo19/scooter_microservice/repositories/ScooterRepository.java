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

    /** No necesita query, lo resuelve JPA a partir del nombre*/
    int countByState(ScooterState state);

    @Query("SELECT s FROM Scooter s WHERE s.latitude <= (:latitude + :radio) AND s.latitude > (:latitude - :radio)" +
            "AND s.longitude <= (:longitude + :radio) AND s.longitude > (:longitude - :radio) ")
    List<Scooter> getScootersByLocation(double latitude, double longitude, double radio);

    @Query("SELECT s.latitude FROM Scooter s WHERE s.id = :id")
    double getLatitude(long id);

    @Query("SELECT s.longitude FROM Scooter s WHERE s.id = :id")
    double getLongitude(long id);
}
