package arquitectura.grupo19.controller;

import arquitectura.grupo19.entity.Carrera;
import arquitectura.grupo19.service.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Carrera> getCarrerasConInscriptos() {
        return carreraService.getCarrerasConInscriptos();
    }
}
