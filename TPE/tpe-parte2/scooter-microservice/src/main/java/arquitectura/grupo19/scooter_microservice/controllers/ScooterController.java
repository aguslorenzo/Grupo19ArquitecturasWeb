package arquitectura.grupo19.scooter_microservice.controllers;

import arquitectura.grupo19.scooter_microservice.dto.ScooterDto;
import arquitectura.grupo19.scooter_microservice.dto.ScooterStatusCountDto;
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


    @PostMapping("/{scooterId}/start-scooter")
    public void activateScooter(@PathVariable("scooterId") Long id) {
        scooterService.activateScooter(id);
    }

    @PutMapping("/{id}/stop")
    public void stopScooter(@PathVariable Long id) {
        scooterService.deactivateScooter(id);
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
    public boolean isScooterInAllowedLocation(@PathVariable Long id) {
        return scooterService.checkIfScooterIsInAllowedLocation(id);
    }

    @PutMapping("/maintenance/{id}")
    public void putScooterOnMaintenance(@PathVariable Long id) {
        scooterService.putScooterOnMaintenance(id);
    }

    @PutMapping("/toggle-status/{id}")
    public void toggleStatus(@PathVariable Long id) {
        scooterService.toggleStatus(id);
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
    public void addTimeOfUse(@PathVariable Long id, @PathVariable int time) {
        scooterService.addTimeOfUse(id, time);
    }

    @GetMapping("/{id}/kilometers")
    public ResponseEntity<Double> getKilometers(@PathVariable long id) {
        try {
            Double kms = scooterService.getKilometers(id);
            return ResponseEntity.ok(kms);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0.0);
        }
    }

    @GetMapping("/{id}/usageTime")
    public int getUsageTime(@PathVariable long id){
        return scooterService.getUsageTime(id);
    }

    @GetMapping("/{id}/activeTime")
    public int getActiveTime(@PathVariable long id){
        return scooterService.getActiveTime(id);
    }

    @GetMapping("/{id}/maintenance-status")
    public ResponseEntity<Boolean> checkMaintenanceStatus(@PathVariable Long id) {
        boolean needsMaintenance = scooterService.checkMaintenanceStatus(id);
        return ResponseEntity.ok(needsMaintenance);
    }

    @GetMapping("/trips")
    public List<ScooterDto> getScootersWithTrips(@RequestParam int year, @RequestParam int minTrips) {
        return scooterService.findScootersWithTrips(year, minTrips);
    }

    @GetMapping("/status-count")
    public ScooterStatusCountDto getScooterStatusCounts() {
        return scooterService.getScooterStatusCounts();
    }

    @GetMapping("/latitude/{latitude}/longitude/{longitude}/radio/{radio}")
    public List<ScooterDto> getScootersByLocation(@PathVariable double latitude, @PathVariable double longitude, @PathVariable double radio){
        return scooterService.getScootersByLocation(latitude, longitude, radio);
    }

}
