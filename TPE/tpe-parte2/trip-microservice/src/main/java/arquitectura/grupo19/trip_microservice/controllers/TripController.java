package arquitectura.grupo19.trip_microservice.controllers;

import arquitectura.grupo19.trip_microservice.dto.TripDto;
import arquitectura.grupo19.trip_microservice.dto.TripRequestDto;
import arquitectura.grupo19.trip_microservice.dto.TripResponseDto;
import arquitectura.grupo19.trip_microservice.repositories.TripRepository;
import arquitectura.grupo19.trip_microservice.services.TripService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TripDto> getTrips() {
        return tripService.getTrips();
    }


    @PostMapping("/{userId}/{scooterId}")
    public ResponseEntity<?> createTrip(@PathVariable long userId, @PathVariable long scooterId){
        TripResponseDto result = tripService.createTrip(userId,scooterId);
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

    @GetMapping("/scooters-by-trips")
    public List<Long> findScootersWithMinTrips(@RequestParam int year, @RequestParam int minTrips) {
        return tripService.findScootersWithMinTrips(year, minTrips);
    }

    @GetMapping("/total-billed")
    public double getTotalBilledInPeriod(@RequestParam int year,
                                         @RequestParam int startMonth,
                                         @RequestParam int endMonth) {
        return tripService.getTotalBilledInPeriod(year, startMonth, endMonth);
    }

    private ResponseEntity<TripResponseDto> buildResponse(TripResponseDto responseDto) {
        if (responseDto.isSuccess()) {
            return ResponseEntity.accepted().body(responseDto);
        }
        return ResponseEntity.badRequest().body(responseDto);
    }
}
