package arquitectura.grupo19.stop_microservice.controllers;

import arquitectura.grupo19.stop_microservice.dto.StopDto;
import arquitectura.grupo19.stop_microservice.services.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("stops")
public class StopController {

    @Autowired
    private StopService stopService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StopDto> getStops() {
        return stopService.getStops();
    }

    @GetMapping("/{id}")
    public StopDto getStopById(@PathVariable String id) {
        return stopService.getStopById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveStop(@RequestBody StopDto stopDto) {
    	stopService.saveStop(stopDto);
    }

    @PutMapping("/{id}")
    public void updateStop(@PathVariable String id, @RequestBody StopDto stopDto) {
    	stopService.updateStop(id, stopDto);
    }

    @DeleteMapping("/{id}")
    public StopDto deleteStop(@PathVariable String id) {
        return stopService.deleteStop(id);
    }

}
