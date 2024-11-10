package arquitectura.grupo19.trip_microservice.controllers;

import arquitectura.grupo19.trip_microservice.dto.TripRequestDto;
import arquitectura.grupo19.trip_microservice.dto.TripResponseDto;
import arquitectura.grupo19.trip_microservice.entities.Trip;
import arquitectura.grupo19.trip_microservice.services.TripService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    // Método para generar un viaje
    @PostMapping("/new")
    public ResponseEntity<?> createTrip(@RequestBody @Valid TripRequestDto tripRequestDTO){
        TripResponseDto result = tripService.createTrip(tripRequestDTO);
        return buildResponse(result);
    }

    @PatchMapping("/endtrip/{tripId}")
    public ResponseEntity<?> endTrip(@PathVariable long tripId){
        TripResponseDto result = this.tripService.endTrip(tripId);
        return buildResponse(result);
    }

    private ResponseEntity<TripResponseDto> buildResponse(TripResponseDto responseDto) {
        if (responseDto.isSuccess()) {
            return ResponseEntity.accepted().body(responseDto);
        }
        return ResponseEntity.badRequest().body(responseDto);
    }
}
