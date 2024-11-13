package arquitectura.grupo19.admin_microservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import arquitectura.grupo19.admin_microservice.entities.Admin;


@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
