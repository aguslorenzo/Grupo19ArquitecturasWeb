package arquitectura.grupo19.repository;

import arquitectura.grupo19.entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera,Integer> {

}
