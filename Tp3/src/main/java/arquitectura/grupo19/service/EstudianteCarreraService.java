package arquitectura.grupo19.service;

import arquitectura.grupo19.dto.EstudianteCarreraDTO;
import arquitectura.grupo19.entity.Carrera;
import arquitectura.grupo19.entity.Estudiante;
import arquitectura.grupo19.entity.EstudianteCarrera;
import arquitectura.grupo19.repository.CarreraRepository;
import arquitectura.grupo19.repository.EstudianteCarreraRepository;
import arquitectura.grupo19.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
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
    public List<EstudianteCarreraDTO> obtenerMatriculas(){
        return estudianteCarreraRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // MATRICULAR ESTUDIANTE
    public EstudianteCarreraDTO matricularEstudiante(int estudianteId, int carreraId) {
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
        EstudianteCarrera savedMatricula = estudianteCarreraRepository.save(nuevaMatricula);

        return convertToDTO(savedMatricula);
    }

    // REPORTE DE CARRERAS (inscriptos y egresados por año) ORDENADAS ALFABETICAMENTE Y AÑOS ORDENADOS DE MANERA CRONOLOGICA
    public List<EstudianteCarreraDTO> getReporteDeCarrerasPorAnio(){
        List<EstudianteCarreraDTO> result = new ArrayList<>();

        List<Carrera> carreras = carreraRepository.findAll(Sort.by("nombre").ascending());
        List<Integer> anios = this.getAnios();

        for (Carrera carrera : carreras) {
            for (Integer anio : anios) {
                Integer inscriptos = countInscriptosByCarreraAndAnio(carrera.getId(), anio);
                Integer egresados = countEgresadosByCarreraAndAnio(carrera.getId(), anio);
                if (inscriptos == null) inscriptos = 0;
                if (egresados == null) egresados = 0;

                if(inscriptos != 0 || egresados != 0)
                    result.add(new EstudianteCarreraDTO(carrera.getNombre(), anio, inscriptos, egresados));
            }
        }
        return result;
    }

    //Métodos aux para generar reporte
    public Integer countInscriptosByCarreraAndAnio(int carrera, int anio) {
        return estudianteCarreraRepository.countInscriptosByCarreraAndAnio(carrera, anio);
    }

    public Integer countEgresadosByCarreraAndAnio(int carrera, int anio) {
        return estudianteCarreraRepository.countEgresadosByCarreraAndAnio(carrera, anio);
    }

    private List<Integer> getAnios() {
        List<Integer> aniosInscripcion = estudianteCarreraRepository.getAniosInscripcion();
        List<Integer> aniosGraduacion = estudianteCarreraRepository.getAniosGraduacion();

        // Combinar y eliminar duplicados
        Set<Integer> aniosUnicos = new HashSet<>();
        aniosUnicos.addAll(aniosInscripcion);
        aniosUnicos.addAll(aniosGraduacion);

        List<Integer> anios = new ArrayList<>(aniosUnicos);
        Collections.sort(anios);
        return anios;
    }

    private EstudianteCarreraDTO convertToDTO(EstudianteCarrera matricula) {
        int id = matricula.getId();
        int idEstudiante = matricula.getEstudiante().getDni();
        int idCarrera = matricula.getCarrera().getId();
        int anioInscripcion = matricula.getInscripcion();
        int anioGraduacion = matricula.getGraduacion();
        String carrera = matricula.getCarrera().getNombre();

        return new EstudianteCarreraDTO(id, idEstudiante, idCarrera, anioInscripcion, anioGraduacion, carrera);
    }

}
