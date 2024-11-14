package arquitectura.grupo19.map_microservice.repositories;

import arquitectura.grupo19.map_microservice.entities.ScooterLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MapRepository extends JpaRepository<ScooterLocation, Long> {
    @Query("SELECT s FROM ScooterLocation s WHERE FUNCTION('distance', s.gpsLocation, :location) < :radius")
    List<ScooterLocation> findScootersNearby(@Param("location") String location, @Param("radius") double radius);
}
