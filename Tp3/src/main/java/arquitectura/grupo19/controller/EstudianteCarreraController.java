package arquitectura.grupo19.controller;

import arquitectura.grupo19.dto.EstudianteCarreraDTO;
import arquitectura.grupo19.entity.EstudianteCarrera;
import arquitectura.grupo19.service.EstudianteCarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matriculas")
public class EstudianteCarreraController {

    @Autowired
    private EstudianteCarreraService estudianteCarreraService;

    @GetMapping
    public ResponseEntity<List<EstudianteCarreraDTO>> obtenerMatriculas() {
        List<EstudianteCarreraDTO> matriculas = estudianteCarreraService.obtenerMatriculas();
        return ResponseEntity.ok(matriculas);
    }

    @PostMapping("/estudiante/{idEstudiante}/carrera/{idCarrera}")
    public ResponseEntity<?> matricularEstudiante(@PathVariable int idEstudiante, @PathVariable int idCarrera) {
        try {
            EstudianteCarrera matricula = estudianteCarreraService.matricularEstudiante(idEstudiante, idCarrera);
            return new ResponseEntity<>(matricula, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
