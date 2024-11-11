package arquitectura.grupo19.scooter_microservice.controllers;

import arquitectura.grupo19.scooter_microservice.dto.ScooterDto;
import arquitectura.grupo19.scooter_microservice.exceptions.NotFoundException;
import arquitectura.grupo19.scooter_microservice.services.ScooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("scooters")
public class ScooterController {

    @Autowired
    private ScooterService scooterService;

    /******************************************************************************/

    @PostMapping("/{id}/start/trip/{tripId}")
    public ScooterDto startScooter(@PathVariable("id") Long id, @PathVariable("tripId") Long tripId) {
        return scooterService.activateScooter(id, tripId);
    }

    @PostMapping("/{id}/stop")
    public ScooterDto stopScooter(@PathVariable Long id) {
        return scooterService.deactivateScooter(id);
    }

    @PostMapping("/{id}/pause")
    public ScooterDto pauseScooter(@PathVariable Long id) {
        return scooterService.pauseScooter(id);
    }

    @PostMapping("/{id}/restart")
    public ScooterDto restartScooter(@PathVariable Long id) {
        return scooterService.restartScooter(id);
    }

    @GetMapping("/{id}/check-location")
    public boolean checkLocation(@PathVariable Long id, @RequestParam String location) {
        return scooterService.checkIfScooterIsInAllowedLocation(id, location);
    }

    @GetMapping("/{id}/available")
    public ResponseEntity<Boolean> isAvailable(@PathVariable long id) {
        try {
            boolean available = scooterService.isAvailable(id);
            return ResponseEntity.ok(available);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }

    @PostMapping("/{id}/triptime/{time}")
    public void addTimeOfUse(@PathVariable Long id, @PathVariable Long time) {
        scooterService.addTimeOfUse(id, time);
    }

    /******************************************************************************/

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ScooterDto> getScooters() {
        return scooterService.getScooters();
    }

    @GetMapping("/{id}")
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
