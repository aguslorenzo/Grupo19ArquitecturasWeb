package arquitectura.grupo19.scooter_microservice.controllers;

import arquitectura.grupo19.scooter_microservice.dto.ScooterDto;
import arquitectura.grupo19.scooter_microservice.services.ScooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/scooters")
public class ScooterController {

    @Autowired
    private ScooterService scooterService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ScooterDto> getScooters() {
        return scooterService.getScooters();
    }

    @GetMapping("/id/{id}")
    public ScooterDto getScooterById(@PathVariable Long id) {
        return scooterService.getScooterById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveScooter(@RequestBody ScooterDto scooterDto) {
    	scooterService.saveScooter(scooterDto);
    }

    @PutMapping("/{id}")
    public void updateScooter(@PathVariable Long id, @RequestBody ScooterDto scooterDto) {
    	scooterService.updateScooter(id, scooterDto);
    }

    @DeleteMapping("/{id}")
    public ScooterDto deleteScooter(@PathVariable Long id) {
        return scooterService.deleteScooter(id);
    }
    
    @PutMapping("/maintenance/{id}")
    public void putScooterOnMaintenance(@PathVariable Long id) {
        scooterService.putScooterOnMaintenance(id);
    }

    @PutMapping("/available/{id}")
    public void putScooterAvailable(@PathVariable Long id) {
        scooterService.putScooterAvailable(id);
    }
    
}
