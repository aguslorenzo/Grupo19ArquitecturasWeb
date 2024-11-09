package arquitectura.grupo19.service;

import arquitectura.grupo19.dto.EstudianteDTO;
import arquitectura.grupo19.entity.Estudiante;

import arquitectura.grupo19.exceptions.EstudianteNotFoundException;
import arquitectura.grupo19.exceptions.InvalidGenderException;
import arquitectura.grupo19.repository.EstudianteRepository;
import arquitectura.grupo19.utils.constantes.Genero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteService {

    /**
     * Antes:
     * @Autowired
     * private EstudianteRepository estudianteRepository;
     */

    /**
     * Utilizando la inyección de dependencias automática de Spring
     */
    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    // OBTENER TODOS LOS ESTUDIANTES
    @Transactional(readOnly = true)
    public List<EstudianteDTO> obtenerEstudiantes() {
        return estudianteRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * TRANSACTIONAL: Es recomendable marcar métodos de la capa de servicio con la anotación @Transactional,
     * especialmente para aquellos que modifican la base de datos. Esto asegura que se manejen
     * las transacciones correctamente, y en caso de error, se realice un rollback automático.
     */

    // DAR DE ALTA UN ESTUDIANTE
    @Transactional
    public EstudianteDTO guardarEstudiante(EstudianteDTO estudiante) {
        verificarDNIUnico(estudiante.getDni());
        Estudiante nuevoEstudiante = estudianteRepository.save(convertToEntity(estudiante));
        return convertToDTO(nuevoEstudiante);
    }

    // OBTENER ESTUDIANTES ORDENADOS POR CUALQUIER CRITERIO Y ORDEN
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public EstudianteDTO buscarEstudiantePorNroLibreta(int nroLibreta) {
        Estudiante estudiante = estudianteRepository.findByNroLibreta(nroLibreta)
                .orElseThrow(() -> new EstudianteNotFoundException("No se encontró un estudiante con el número de libreta " + nroLibreta));
        return convertToDTO(estudiante);
    }

    // RECUPERAR ESTUDIANTES POR GENERO
    @Transactional(readOnly = true)
    public List<EstudianteDTO> obtenerEstudiantesPorGenero(String genero) {
        validarGenero(genero);
        return estudianteRepository.findByGenero(genero)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // OBTENER ESTUDIANTE SEGUN CARRERA FILTRANDO CIUDAD
    @Transactional(readOnly = true)
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
     * @param genero, si se agregasen nuevos al csv habría que incluirlos en el enum Genero
     * @return input valido o invalido
     */
    private void validarGenero(String genero) {
        if (!Genero.esGeneroValido(genero)) {
            throw new InvalidGenderException("El género proporcionado es inválido.");
        }
    }

    private EstudianteDTO convertToDTO(Estudiante estudiante) {
        return new EstudianteDTO(estudiante.getNroLibreta(),estudiante.getNombre(),estudiante.getApellido(),estudiante.getEdad(),estudiante.getGenero(),estudiante.getDni(),estudiante.getCiudad());
    }

    private Estudiante convertToEntity(EstudianteDTO estudianteDto){
        return new Estudiante(estudianteDto.getNombre(),estudianteDto.getApellido(),estudianteDto.getEdad(),estudianteDto.getGenero(), estudianteDto.getDni(), estudianteDto.getCiudad(), estudianteDto.getNroLibreta());
    }

}
