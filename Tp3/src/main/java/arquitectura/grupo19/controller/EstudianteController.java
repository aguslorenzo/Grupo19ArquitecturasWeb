package arquitectura.grupo19.controller;

import arquitectura.grupo19.dto.EstudianteDTO;
import arquitectura.grupo19.exceptions.EstudianteNotFoundException;
import arquitectura.grupo19.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @GetMapping
    public ResponseEntity<List<EstudianteDTO>> obtenerEstudiantes() {
        List<EstudianteDTO> estudiantes = estudianteService.obtenerEstudiantes();
        return ResponseEntity.ok(estudiantes);
    }

    @PostMapping
    public ResponseEntity<?> altaEstudiante(@RequestBody @Valid EstudianteDTO estudianteDTO) {
        if(estudianteDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El estudiante no puede ser nulo.");
        }
        try{
            EstudianteDTO nuevoEstudiante = estudianteService.guardarEstudiante(estudianteDTO);
            return ResponseEntity.ok(nuevoEstudiante);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ordenados")
    public ResponseEntity<?> obtenerEstudiantesOrdenadosPorCriterio(
            @RequestParam String criterio,
            @RequestParam(defaultValue = "asc") String orden) {

        if (criterio == null || criterio.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El criterio de ordenación no puede ser nulo o vacío.");
        }

        try {
            List<EstudianteDTO> estudiantesOrdenados = estudianteService.obtenerEstudiantesOrdenadosPorCriterio(criterio, orden);
            return ResponseEntity.ok(estudiantesOrdenados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error al ordenar los estudiantes: " + e.getMessage());
        }
    }

    @GetMapping("/nrolibreta/{nroLibreta}")
    public ResponseEntity<?> obtenerEstudiante(@PathVariable int nroLibreta) {
        if (nroLibreta <= 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El número de libreta debe ser un entero positivo.");
        try {
            EstudianteDTO estudiante = estudianteService.buscarEstudiantePorNroLibreta(nroLibreta);
            return ResponseEntity.ok(estudiante);
        } catch (EstudianteNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/genero")
    public ResponseEntity<?> obtenerEstudiantesPorGenero(@RequestParam String genero) {

        if (genero == null || genero.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El género no puede ser nulo o vacío.");
        }

        try {
            List<EstudianteDTO> estudiantes = estudianteService.obtenerEstudiantesPorGenero(genero);
            return ResponseEntity.ok(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error al recuperar los estudiantes: " + e.getMessage());
        }
    }

    @GetMapping("/filtrados")
    public ResponseEntity<?> obtenerEstudiantesPorCarreraFiltrados(@RequestParam String carrera, @RequestParam String ciudad){

        if (carrera == null || carrera.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La carrera no puede ser nula o vacía.");
        }

        if (ciudad == null || ciudad.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La ciudad no puede ser nula o vacía.");
        }

    	try {
            List<EstudianteDTO> estudiantesFiltrados = new ArrayList<>(estudianteService.obtenerEstudiantesPorCarreraFiltrados(carrera, ciudad));
            return ResponseEntity.ok(estudiantesFiltrados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest().body("Errores de validación: " + errorMessage);
    }
}
