package arquitectura.grupo19.repository;

import arquitectura.grupo19.dto.CarreraDTO;
import arquitectura.grupo19.entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera,Integer> {
    @Query("SELECT c FROM Carrera c " +
           "JOIN EstudianteCarrera ec ON ec.carrera.id = c.id " +
           "GROUP BY c.id, c.nombre, c.duracion " +
           "HAVING COUNT(ec.estudiante) > 0" +
             "ORDER BY COUNT(ec.estudiante) DESC")
    List<Carrera> getCarrerasConInscriptos();
}
