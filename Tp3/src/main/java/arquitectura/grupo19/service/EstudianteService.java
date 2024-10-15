package arquitectura.grupo19.service;

import arquitectura.grupo19.dto.EstudianteDTO;
import arquitectura.grupo19.entity.Estudiante;

import arquitectura.grupo19.exceptions.EstudianteNotFoundException;
import arquitectura.grupo19.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteService {
    @Autowired
    private EstudianteRepository estudianteRepository;

    public List<EstudianteDTO> obtenerEstudiantes() {
        return estudianteRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EstudianteDTO guardarEstudiante(Estudiante estudiante) {
        Estudiante nuevoEstudiante = estudianteRepository.save(estudiante);
        return convertToDTO(nuevoEstudiante);
    }

    // OBTENER ESTUDIANTES POR CRITERIO Y ORDEN
    public List<EstudianteDTO> obtenerEstudiantesOrdenadosPorCriterio(String criterio, Boolean asc) {
        List<Estudiante> estudiantes;

        if (asc) {
            estudiantes = estudianteRepository.findAll(Sort.by(criterio).ascending());
        } else {
            estudiantes = estudianteRepository.findAll(Sort.by(criterio).descending());
        }

        return estudiantes.stream()
                .map(this::convertToDTO)  // Asumiendo que tienes el método convertToDTO para convertir Estudiante a EstudianteDTO
                .collect(Collectors.toList());
    }
    
    // OBTENER ESTUDIANTE SEGUN CARRERA FILTRANDO CIUDAD
    public List<EstudianteDTO> obtenerEstudiantesPorCarreraFiltrados(String carrera, String ciudad) {
        return estudianteRepository.obtenerEstudiantesPorCarreraFiltrados(carrera, ciudad)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // RECUPERAR UN ESTUDIANTE EN BASE A SU NUMERO DE LIBRETA UNIVERSITARIA
    public EstudianteDTO buscarEstudiantePorNroLibreta(int nroLibreta) {
        Estudiante estudiante = estudianteRepository.findByNroLibreta(nroLibreta)
                .orElseThrow(() -> new EstudianteNotFoundException("No se encontró un estudiante con el número de libreta " + nroLibreta));
        return convertToDTO(estudiante);
    }

    // RECUPERAR ESTUDIANTES POR GENERO
    public List<EstudianteDTO> obtenerEstudiantesPorGenero(String genero) {
        return estudianteRepository.findByGenero(genero)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private EstudianteDTO convertToDTO(Estudiante estudiante) {
        return new EstudianteDTO(estudiante.getNroLibreta(),estudiante.getNombre(),estudiante.getApellido(),estudiante.getEdad(),estudiante.getGenero(),estudiante.getDni(),estudiante.getCiudad(),null);
    }

}
