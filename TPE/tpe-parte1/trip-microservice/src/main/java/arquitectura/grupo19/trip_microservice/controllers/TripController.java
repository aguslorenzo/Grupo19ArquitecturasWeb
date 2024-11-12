package arquitectura.grupo19.trip_microservice.controllers;

import arquitectura.grupo19.trip_microservice.dto.TripRequestDto;
import arquitectura.grupo19.trip_microservice.dto.TripResponseDto;
import arquitectura.grupo19.trip_microservice.services.TripService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    // Método para generar un viaje
    @PostMapping
    public ResponseEntity<?> createTrip(@RequestBody @Valid TripRequestDto tripRequestDTO){
        TripResponseDto result = tripService.createTrip(tripRequestDTO);
        return buildResponse(result);
    }

    @PatchMapping("/endtrip/{tripId}")
    public ResponseEntity<?> endTrip(@PathVariable long tripId){
        TripResponseDto result = tripService.endTrip(tripId);
        return buildResponse(result);
    }

    @PutMapping("/{tripId}/updateamount")
    public ResponseEntity<?> updateTripWithAdditionalCharge(@PathVariable long tripId){
        TripResponseDto result = tripService.updateTripWithAdditionalCharge(tripId);
        return buildResponse(result);
    }

    private ResponseEntity<TripResponseDto> buildResponse(TripResponseDto responseDto) {
        if (responseDto.isSuccess()) {
            return ResponseEntity.accepted().body(responseDto);
        }
        return ResponseEntity.badRequest().body(responseDto);
    }
}
