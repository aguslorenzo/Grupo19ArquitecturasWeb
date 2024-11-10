package arquitectura.grupo19.trip_microservice.repositories;

import arquitectura.grupo19.trip_microservice.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query("SELECT t.userId FROM Trip t WHERE t.userId = :userId AND t.endDateTime != null")
    boolean existsByUserIdAndEndDateTimeIsNull(Long userId);

    // TODO implementar query
    @Query("")
    List<Trip> findByEndDateTimeIsNull();
}
