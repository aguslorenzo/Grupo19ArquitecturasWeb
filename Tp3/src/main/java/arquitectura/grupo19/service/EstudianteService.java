package arquitectura.grupo19.service;

import arquitectura.grupo19.entity.Estudiante;
import arquitectura.grupo19.exceptions.EstudianteNotFoundException;
import arquitectura.grupo19.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    // RECUPERAR UN ESTUDIANTE EN BASE A SU NUMERO DE LIBRETA UNIVERSITARIA
    public Estudiante buscarEstudiantePorNroLibreta(int nroLibreta) {
        return estudianteRepository.findByNroLibreta(nroLibreta)
                .orElseThrow(() -> new EstudianteNotFoundException("No se encontró un estudiante con el número de libreta " + nroLibreta));
    }
    public List<Estudiante> obtenerEstudiantesPorGenero(String genero){
        return estudianteRepository.findByGenero(genero);
    }
}
