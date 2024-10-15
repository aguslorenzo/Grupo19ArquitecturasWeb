package arquitectura.grupo19.repository;

import arquitectura.grupo19.entity.Carrera;
import arquitectura.grupo19.entity.Estudiante;
import arquitectura.grupo19.entity.EstudianteCarrera;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteCarreraRepository extends JpaRepository<EstudianteCarrera,Integer>{

    boolean existsByEstudianteAndCarrera(Estudiante estudiante, Carrera carrera);

    @Query("SELECT ec.inscripcion FROM EstudianteCarrera ec WHERE ec.inscripcion IS NOT NULL")
	List<Integer> getAniosInscripcion();

    @Query("SELECT ec.graduacion FROM EstudianteCarrera ec WHERE ec.graduacion IS NOT NULL AND ec.graduacion > 0")
	List<Integer> getAniosGraduacion();

    @Query("SELECT COUNT(ec) FROM EstudianteCarrera ec WHERE ec.carrera.id = :carrera AND ec.inscripcion = :anio")
    Integer countInscriptosByCarreraAndAnio(@Param("carrera") int carrera, @Param("anio") int anio);

    @Query("SELECT COUNT(ec) FROM EstudianteCarrera ec WHERE ec.carrera.id = :carrera AND ec.graduacion = :anio AND ec.graduacion > 0")
    Integer countEgresadosByCarreraAndAnio(@Param("carrera") int carrera, @Param("anio") int anio);




	
}
