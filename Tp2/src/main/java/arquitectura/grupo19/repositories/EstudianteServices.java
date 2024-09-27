package arquitectura.grupo19.repositories;

import java.util.List;

import arquitectura.grupo19.entities.Estudiante;

public interface EstudianteServices extends Repository<Estudiante>{

	List<Estudiante> obtenerEstudiantesPorGenero(String genero);

}
