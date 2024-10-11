package arquitectura.grupo19.controller;

import arquitectura.grupo19.dto.EstudianteCarreraDTO;
import arquitectura.grupo19.entity.EstudianteCarrera;
import arquitectura.grupo19.service.EstudianteCarreraService;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView(EstudianteCarreraDTO.VistaBase.class)
    public ResponseEntity<List<EstudianteCarreraDTO>> obtenerMatriculas() {
        List<EstudianteCarreraDTO> matriculas = estudianteCarreraService.obtenerMatriculas();
        return ResponseEntity.ok(matriculas);
    }

    /*
    TODO charlar si dejamos así y por defecto la fecha es la actual + antiguedad y graduacion en 0 o damos opcion a mas datos
    TODO tambien charlar si esto lo dejamos acá o sacamos los endpoints de este controller a estudianteController y carreraController
    Sino no tiene sentido tener "generar reporte" en carrera y tambien debería estar aca
    Mismo con el método de getCarrerasConInscriptos, tambien deberia estar acá
    y el controller de carrera quedaría solo para operaciones "basicas" de crud para carrera
     */
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
