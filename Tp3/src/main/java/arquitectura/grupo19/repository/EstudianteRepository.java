package arquitectura.grupo19.repository;

import arquitectura.grupo19.dto.EstudianteDTO;
import arquitectura.grupo19.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

	/**
	 * Optional: obliga a manejar la posibilidad de que el valor esté presente o
	 * ausente.
	 */
	Optional<Estudiante> findByNroLibreta(int nroLibreta);

	List<Estudiante> findByGenero(String genero);

	@Query("SELECT e FROM Estudiante e " + "JOIN EstudianteCarrera ec ON e.dni = ec.estudiante.dni "
			+ "JOIN Carrera c ON ec.carrera.id = c.id " + "WHERE e.ciudad = :ciudad AND c.nombre = :carrera")
	List<Estudiante> obtenerEstudiantesPorCarreraFiltrados(@Param("carrera") String carrera,
			@Param("ciudad") String ciudad);

}
