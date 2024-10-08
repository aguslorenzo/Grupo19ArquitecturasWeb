package arquitectura.grupo19.repository;


import arquitectura.grupo19.entity.Carrera;
import arquitectura.grupo19.entity.Estudiante;
import arquitectura.grupo19.entity.EstudianteCarrera;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteCarreraRepository extends JpaRepository<EstudianteCarrera,Integer>{

    boolean existsByEstudianteAndCarrera(Estudiante estudiante, Carrera carrera);
}
