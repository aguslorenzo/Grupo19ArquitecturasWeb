package arquitectura.grupo19.map_microservice.controllers;

import arquitectura.grupo19.map_microservice.dto.ScooterDto;
import arquitectura.grupo19.map_microservice.entities.ScooterLocation;
import arquitectura.grupo19.map_microservice.services.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ScooterDto>> getNearbyScooters(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam double radius) {
        try {
            List<ScooterDto> nearbyScooters = mapService.findScootersNearby(latitude, longitude, radius);
            return ResponseEntity.ok(nearbyScooters);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
