package arquitectura.grupo19.map_microservice.controllers;

import arquitectura.grupo19.map_microservice.entities.ScooterLocation;
import arquitectura.grupo19.map_microservice.services.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("maps")
public class MapController {

    @Autowired
    private MapService mapService;

    /**
     * Si un usuario necesita un monopatín podrá encontrar el más cercano a través
     * de un mapa interactivo en la app que muestra los monopatines en la zona.
     */
    @GetMapping("/scooters/nearby")
    public List<ScooterLocation> getNearbyScooters(@RequestParam double latitude,
                                                   @RequestParam double longitude,
                                                   @RequestParam double radius) {
        return mapService.findScootersNearby(latitude, longitude, radius);
    }


}
