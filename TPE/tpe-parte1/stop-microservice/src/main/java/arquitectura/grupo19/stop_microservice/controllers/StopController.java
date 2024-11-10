package arquitectura.grupo19.stop_microservice.controllers;

import arquitectura.grupo19.stop_microservice.dto.StopDto;
import arquitectura.grupo19.stop_microservice.services.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/stops")
public class StopController {

    @Autowired
    private StopService stopService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StopDto> getStops() {
        return stopService.getStops();
    }

    @GetMapping("/id/{id}")
    public StopDto getStopById(@PathVariable Long id) {
        return stopService.getStopById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveStop(@RequestBody StopDto stopDto) {
    	stopService.saveStop(stopDto);
    }

    @PutMapping("/{id}")
    public void updateStop(@PathVariable Long id, @RequestBody StopDto stopDto) {
    	stopService.updateStop(id, stopDto);
    }

    @DeleteMapping("/{id}")
    public StopDto deleteStop(@PathVariable Long id) {
        return stopService.deleteStop(id);
    }
    
    @PutMapping("/{stopId}/scooter/{scooterId}")
    public void placeScooterInStop(@PathVariable Long stopId, @PathVariable Long scooterId) {
        stopService.placeScooter(stopId, scooterId);
    }

}
