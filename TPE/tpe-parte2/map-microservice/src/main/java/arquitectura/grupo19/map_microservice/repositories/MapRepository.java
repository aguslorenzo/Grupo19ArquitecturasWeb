package arquitectura.grupo19.map_microservice.repositories;

import arquitectura.grupo19.map_microservice.entities.ScooterLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MapRepository extends JpaRepository<ScooterLocation, Long> {

    @Query(value = "SELECT s FROM ScooterLocation s WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(s.latitude)) " +
            "* cos(radians(s.longitude) - radians(:longitude)) " +
            "+ sin(radians(:latitude)) * sin(radians(s.latitude)))) < :radius")
    List<ScooterLocation> findScootersNearby(@Param("latitude") double latitude,
                                             @Param("longitude") double longitude,
                                             @Param("radius") double radius);

}

