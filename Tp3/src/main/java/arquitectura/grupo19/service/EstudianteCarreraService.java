package arquitectura.grupo19.service;

import arquitectura.grupo19.dto.EstudianteCarreraDTO;
import arquitectura.grupo19.entity.Carrera;
import arquitectura.grupo19.entity.Estudiante;
import arquitectura.grupo19.entity.EstudianteCarrera;
import arquitectura.grupo19.repository.CarreraRepository;
import arquitectura.grupo19.repository.EstudianteCarreraRepository;
import arquitectura.grupo19.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteCarreraService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private EstudianteCarreraRepository estudianteCarreraRepository;

    // OBTENER TODAS LAS MATRICULAS
    /**
     * Convierte entidades en un DTO que tiene solo los atributos necesarios. Esto sirve para
     * tener un control más claro sobre que datos se envían en las respuestas JSON
     * y evitar problemas de serialización con Hibernate.
     */
    public List<EstudianteCarreraDTO> obtenerMatriculas(){
        return estudianteCarreraRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private EstudianteCarreraDTO convertToDTO(EstudianteCarrera estudianteCarrera) {
        int id = estudianteCarrera.getId();
        int idEstudiante = estudianteCarrera.getEstudiante().getDni();
        int idCarrera = estudianteCarrera.getCarrera().getId();
        int anioInscripcion = estudianteCarrera.getInscripcion();
        int anioGraduacion = estudianteCarrera.getGraduacion();

        return new EstudianteCarreraDTO(id, idEstudiante, idCarrera, anioInscripcion, anioGraduacion);
    }

    // MATRICULAR ESTUDIANTE
    public EstudianteCarrera matricularEstudiante(int estudianteId, int carreraId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        Carrera carrera = carreraRepository.findById(carreraId)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));

        // Verificamos si ya está inscripto en la carrera
        if (estudianteCarreraRepository.existsByEstudianteAndCarrera(estudiante, carrera)) {
            throw new RuntimeException("El estudiante ya está matriculado en esta carrera.");
        }

        // Obtenemos el año actual para inscribirlo
        int anioActual = LocalDate.now().getYear();

        EstudianteCarrera nuevaMatricula  = new EstudianteCarrera(estudiante, carrera, anioActual);

        return estudianteCarreraRepository.save(nuevaMatricula);
    }
}
