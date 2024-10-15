package arquitectura.grupo19.service;

import arquitectura.grupo19.dto.EstudianteDTO;
import arquitectura.grupo19.entity.Estudiante;

import arquitectura.grupo19.exceptions.EstudianteNotFoundException;
import arquitectura.grupo19.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    // OBTENER TODOS LOS ESTUDIANTES
    public List<EstudianteDTO> obtenerEstudiantes() {
        return estudianteRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // DAR DE ALTA UN ESTUDIANTE
    public EstudianteDTO guardarEstudiante(EstudianteDTO estudiante) {
        verificarDNIUnico(estudiante.getDni());
        Estudiante nuevoEstudiante = estudianteRepository.save(convertToEntity(estudiante));
        return convertToDTO(nuevoEstudiante);
    }

    // OBTENER ESTUDIANTES ORDENADOS POR CUALQUIER CRITERIO Y ORDEN
    public List<EstudianteDTO> obtenerEstudiantesOrdenadosPorCriterio(String criterio, String orden) {
        // No es necesario validar nulidad de criterio acá, ya fue validado en el controller
        verificarOrden(orden); //verificar que el orden sea "asc" o "desc"
        Sort sort = orden.equalsIgnoreCase("desc") ? Sort.by(criterio).descending() : Sort.by(criterio).ascending();
        return estudianteRepository.findAll(sort)
                .stream()
                .map(this::convertToDTO)  // Asumiendo que tienes el método convertToDTO para convertir Estudiante a EstudianteDTO
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
        validarGenero(genero);
        return estudianteRepository.findByGenero(genero)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // OBTENER ESTUDIANTE SEGUN CARRERA FILTRANDO CIUDAD
    public List<EstudianteDTO> obtenerEstudiantesPorCarreraFiltrados(String carrera, String ciudad) {
        return estudianteRepository.obtenerEstudiantesPorCarreraFiltrados(carrera, ciudad)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private void verificarDNIUnico(int dni) {
        if (estudianteRepository.findById(dni).isPresent()) {
            throw new RuntimeException("Ya existe un estudiante con el DNI: " + dni);
        }
    }

    private void verificarOrden(String orden) {
        if (!orden.equalsIgnoreCase("asc") && !orden.equalsIgnoreCase("desc")) {
            throw new IllegalArgumentException("El orden debe ser 'asc' o 'desc'.");
        }
    }

    /**
     *
     * @param genero, si se agregasen nuevos al csv habría que incluirlos acá
     * @return input valido o invalido
     */
    private void validarGenero(String genero) {
        List<String> generosValidos = Arrays.asList("Male", "Female", "Polygender", "Non-binary", "Masculino",
                "Genderfluid", "Femenino", "Bigender", "Agender");

        if (!generosValidos.contains(genero)) {
            throw new IllegalArgumentException("El género proporcionado es inválido.");
        }
    }

    private EstudianteDTO convertToDTO(Estudiante estudiante) {
        return new EstudianteDTO(estudiante.getNroLibreta(),estudiante.getNombre(),estudiante.getApellido(),estudiante.getEdad(),estudiante.getGenero(),estudiante.getDni(),estudiante.getCiudad());
    }

    private Estudiante convertToEntity(EstudianteDTO estudianteDto){
        return new Estudiante(estudianteDto.getNombre(),estudianteDto.getApellido(),estudianteDto.getEdad(),estudianteDto.getGenero(), estudianteDto.getDni(), estudianteDto.getCiudad(), estudianteDto.getNroLibreta());
    }

}
