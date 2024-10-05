package arquitectura.grupo19.controller;


import arquitectura.grupo19.entity.Estudiante;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String index() {
        Estudiante e = new Estudiante("Isidro","Blackmuir",66,"Male",71779527,"EraChina");
        return "Greetings from Spring Boot!" + e;
    }

}