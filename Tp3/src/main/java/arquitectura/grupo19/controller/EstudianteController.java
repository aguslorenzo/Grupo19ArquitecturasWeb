package arquitectura.grupo19.controller;

import arquitectura.grupo19.dto.EstudianteDTO;
import arquitectura.grupo19.entity.Estudiante;
import arquitectura.grupo19.exceptions.EstudianteNotFoundException;
import arquitectura.grupo19.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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
    public ResponseEntity<EstudianteDTO> altaEstudiante(@RequestBody @Valid Estudiante estudiante) {
        EstudianteDTO nuevoEstudiante = estudianteService.guardarEstudiante(estudiante);
        return ResponseEntity.ok(nuevoEstudiante);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest().body("Errores de validación: " + errorMessage);
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
    
    @GetMapping("/ordenados")
	public ResponseEntity<?> obtenerEstudiantesOrdenadosPorCriterio(@RequestParam  String criterio, @RequestParam  boolean asc) {
		List<EstudianteDTO> estudiantesOrdenados;
		try {
			estudiantesOrdenados = new ArrayList<>(estudianteService.obtenerEstudiantesOrdenadosPorCriterio(criterio, asc));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.ok(estudiantesOrdenados);
	}

    @GetMapping("/genero/{genero}")
    public ResponseEntity<?> obtenerEstudiantesPorGenero(@PathVariable String genero) {
        if (genero == null || genero.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El género no puede estar vacío.");
        }

        if (!esGeneroValido(genero)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El género proporcionado es inválido.");
        }

        List<EstudianteDTO> estudiantes = estudianteService.obtenerEstudiantesPorGenero(genero);

        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/filtrados/{carrera}/{ciudad}")
    public ResponseEntity<?> obtenerEstudiantesPorCarreraFiltrados(@PathVariable String carrera,@PathVariable String ciudad){
    	 if(ciudad==null || ciudad.trim().isEmpty()){
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La ciudad no puede estar vacía.");
         }
    	List<EstudianteDTO> estudiantesFiltrados;
    	try {
    		estudiantesFiltrados = new ArrayList<>(estudianteService.obtenerEstudiantesPorCarreraFiltrados(carrera, ciudad));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.ok(estudiantesFiltrados);
    }

    /**
     *
     * @param genero, si se agregasen nuevos al csv habría que incluirlos acá
     * @return input valido o invalido
     */
    private boolean esGeneroValido(String genero) {
        return Arrays.asList("Male", "Female", "Polygender","Non-binary","Masculino","Genderfluid","Femenino","Bigender","Agender").contains(genero);
    }
}
