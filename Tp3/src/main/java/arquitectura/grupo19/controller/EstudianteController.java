package arquitectura.grupo19.controller;

import arquitectura.grupo19.entity.Estudiante;
import arquitectura.grupo19.exceptions.EstudianteNotFoundException;
import arquitectura.grupo19.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {
    @Autowired
    private EstudianteService estudianteService;

    @GetMapping
    public ResponseEntity<List<Estudiante>> obtenerEstudiantes() {
        List<Estudiante> estudiantes = estudianteService.obtenerEstudiantes();
        return ResponseEntity.ok(estudiantes);
    }

    @PostMapping("/guardar")
    public ResponseEntity<Estudiante> guardarEstudiante(@RequestBody Estudiante estudiante) {
        Estudiante nuevoEstudiante = estudianteService.guardarEstudiante(estudiante);
        return ResponseEntity.ok(nuevoEstudiante);
    }

    @GetMapping("/nrolibreta/{nroLibreta}")
    public ResponseEntity<?> obtenerEstudiante(@PathVariable int nroLibreta) {
        if (nroLibreta <= 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El número de libreta debe ser un entero positivo.");

        try {
            Estudiante estudiante = estudianteService.buscarEstudiantePorNroLibreta(nroLibreta);
            return ResponseEntity.ok(estudiante);
        } catch (EstudianteNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<Estudiante>> obtenerEstudiantesPorGenero(@PathVariable String genero){
        List<Estudiante> estudiantes = estudianteService.obtenerEstudiantesPorGenero(genero);
        return ResponseEntity.ok(estudiantes);
    }

}
