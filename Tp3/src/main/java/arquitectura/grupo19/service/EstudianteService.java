package arquitectura.grupo19.service;

import arquitectura.grupo19.entity.Estudiante;
import arquitectura.grupo19.exceptions.EstudianteNotFoundException;
import arquitectura.grupo19.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {
    @Autowired
    private EstudianteRepository estudianteRepository;

    public List<Estudiante> obtenerEstudiantes(){
        return estudianteRepository.findAll();
    }

    public Estudiante guardarEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    // OBTENER ESTUDIANTES POR CRITERIO Y ORDEN
    // ENDPOINT: http://localhost:8080/estudiantes/obtenerPorCriterio?criterio=nombre&asc=true
    public List<Estudiante> obtenerEstudiantesOrdenadosPorCriterio(String criterio, Boolean asc){
    	if(asc) {
    		return estudianteRepository.findAll(Sort.by(criterio).ascending());
    	}else {
    		return estudianteRepository.findAll(Sort.by(criterio).descending());
    	}	
    }
    
    // RECUPERAR UN ESTUDIANTE EN BASE A SU NUMERO DE LIBRETA UNIVERSITARIA
    public Estudiante buscarEstudiantePorNroLibreta(int nroLibreta) {
        return estudianteRepository.findByNroLibreta(nroLibreta)
                .orElseThrow(() -> new EstudianteNotFoundException("No se encontró un estudiante con el número de libreta " + nroLibreta));
    }
    // RECUPERAR ESTUDIANTES POR GENERO
    public List<Estudiante> obtenerEstudiantesPorGenero(String genero){
        List<Estudiante> estudiantes = estudianteRepository.findByGenero(genero);
        if(estudiantes.isEmpty()){
            throw new EstudianteNotFoundException("No se encontraron estudiantes con el género: " + genero);
        }
        return estudiantes;
    }
}
