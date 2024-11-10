package arquitectura.grupo19.trip_microservice.services;

import arquitectura.grupo19.trip_microservice.dto.TripRequestDto;
import arquitectura.grupo19.trip_microservice.dto.TripResponseDto;
import arquitectura.grupo19.trip_microservice.entities.Trip;
import arquitectura.grupo19.trip_microservice.feignClient.ScooterFeignClient;
import arquitectura.grupo19.trip_microservice.feignClient.UserFeignClient;
import arquitectura.grupo19.trip_microservice.model.Scooter;
import arquitectura.grupo19.trip_microservice.model.User;
import arquitectura.grupo19.trip_microservice.repositories.TripRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final UserFeignClient userFeignClient;
    private final ScooterFeignClient scooterFeignClient;
    private final TripBillingService tripBillingService;

    @Autowired
    public TripService(TripRepository tripRepository, UserFeignClient userFeignClient, ScooterFeignClient scooterFeignClient, TripBillingService tripBillingService) {
        this.tripRepository = tripRepository;
        this.userFeignClient = userFeignClient;
        this.scooterFeignClient = scooterFeignClient;
        this.tripBillingService = tripBillingService;
    }

    @Transactional
    public TripResponseDto createTrip(@Valid TripRequestDto tripRequestDto) {
        TripResponseDto responseDto = new TripResponseDto();

        if(!validateUser(tripRequestDto.getUserId(), responseDto)) return responseDto;
        if (!validateScooter(tripRequestDto.getScooterId(), responseDto)) return responseDto;
        if (!validateUserActiveTrip(tripRequestDto.getUserId(), responseDto)) return responseDto;
        if (!validateStartDate(tripRequestDto.getStartDateTime(), responseDto)) return responseDto;
        if (!tripBillingService.hasSufficientBalance(tripRequestDto.getUserId())) {
            responseDto.setMessage("No hay suficiente saldo en las cuentas asociadas.");
            responseDto.setSuccess(false);
            return responseDto;
        }

        // Crear viaje
        Trip trip = new Trip();
        trip.setUserId(tripRequestDto.getUserId());
        trip.setScooterId(tripRequestDto.getScooterId());
        trip.setStartDateTime(tripRequestDto.getStartDateTime());
        tripRepository.save(trip);

        tripBillingService.startBilling(trip);

        return mapToTripResponseDto(trip, "Viaje creado exitosamente");
    }

    @Transactional
    public TripResponseDto endTrip(long tripId) {
        TripResponseDto responseDto = new TripResponseDto();
        Optional<Trip> trip = tripRepository.findById(tripId);
        /*if(llegoADestino){
            trip.get().setEndDateTime(LocalDateTime.now());

        }*/
        // Preparar respuesta exitosa
        return mapToTripResponseDto(trip.orElse(null), "Finalizó el viaje");
    }

    private boolean validateUser(long userId, TripResponseDto responseDto) {
        User user = userFeignClient.getUserById(userId);
        if (user == null) {
            responseDto.setMessage("Usuario no encontrado.");
            responseDto.setSuccess(false);
            return false;
        }
        return true;
    }

    private boolean validateScooter(long scooterId, TripResponseDto responseDto) {
        Scooter scooter = scooterFeignClient.getScooterById(scooterId);
        if (scooter == null || !scooter.isAvailable()) {
            responseDto.setMessage("El monopatín no está disponible.");
            responseDto.setSuccess(false);
            return false;
        }
        return true;
    }

    // Validar si el usuario ya tiene un viaje activo
    private boolean validateUserActiveTrip(long userId, TripResponseDto responseDto) {
        if (tripRepository.existsByUserIdAndEndDateTimeIsNull(userId)) {
            responseDto.setMessage("El usuario ya tiene un viaje activo.");
            responseDto.setSuccess(false);
            return false;
        }
        return true;
    }

    // Validar fecha y hora de inicio
    private boolean validateStartDate(LocalDateTime startDateTime, TripResponseDto responseDto) {
        if (startDateTime.isAfter(LocalDateTime.now())) {
            responseDto.setMessage("La fecha de inicio no es válida.");
            responseDto.setSuccess(false);
            return false;
        }
        return true;
    }

    private TripResponseDto mapToTripResponseDto(Trip trip, String message) {
        TripResponseDto responseDto = new TripResponseDto();
        responseDto.setScooterId(trip.getScooterId());
        responseDto.setUserId(trip.getUserId());
        responseDto.setFechaHoraInicio(trip.getStartDateTime());
        responseDto.setFechaHoraFin(trip.getEndDateTime());
        responseDto.setMessage(message);
        responseDto.setSuccess(true);
        return responseDto;
    }
}
