package arquitectura.grupo19.controller;

import arquitectura.grupo19.dto.CarreraDTO;
import arquitectura.grupo19.exceptions.CarreraNotFoundException;
import arquitectura.grupo19.service.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carreras")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;

    @GetMapping("/inscriptos")
    public ResponseEntity<?> getCarrerasConInscriptos() {
        try {
            List<CarreraDTO> carreras = carreraService.getCarrerasConInscriptos();
            return ResponseEntity.ok(carreras);
        }
        catch (CarreraNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inseperado.");
        }
    }
}
