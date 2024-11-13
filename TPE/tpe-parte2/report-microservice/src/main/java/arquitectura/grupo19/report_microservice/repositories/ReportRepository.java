package arquitectura.grupo19.report_microservice.repositories;

import arquitectura.grupo19.report_microservice.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("SELECT r FROM Report r WHERE r.scooterId = :scooterId")
    Report findByScooterId(long scooterId);
}
