package arquitectura.grupo19.repository;


import arquitectura.grupo19.entity.EstudianteCarrera;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteCarreraRepository extends JpaRepository<EstudianteCarrera,Integer> {

}
