package arquitectura.grupo19.trip_microservice.repositories;

import arquitectura.grupo19.trip_microservice.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
        boolean existsByUserIdAndEndDateTimeIsNull(Long userId);

    // TODO implementar query
    @Query("")
    List<Trip> findByEndDateTimeIsNull();

    @Query("SELECT t.scooterId FROM Trip t WHERE YEAR(t.startDateTime) = :year GROUP BY t.scooterId HAVING COUNT(t.id) >= :minTrips")
    List<Long> findScootersWithMinTrips(@Param("year") int year, @Param("minTrips") int minTrips);

    @Query("SELECT t FROM Trip t WHERE YEAR(t.startDateTime) = :year AND MONTH(t.startDateTime) BETWEEN :startMonth AND :endMonth")
    List<Trip> findTripsInPeriod(@Param("year") int year,
                                 @Param("startMonth") int startMonth,
                                 @Param("endMonth") int endMonth);
}
