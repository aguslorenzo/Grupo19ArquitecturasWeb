package arquitectura.grupo19.trip_microservice.services;

import arquitectura.grupo19.trip_microservice.dto.TripDto;
import arquitectura.grupo19.trip_microservice.dto.TripResponseDto;
import arquitectura.grupo19.trip_microservice.entities.Trip;
import arquitectura.grupo19.trip_microservice.feignClient.ScooterFeignClient;
import arquitectura.grupo19.trip_microservice.feignClient.StopFeignClient;
import arquitectura.grupo19.trip_microservice.feignClient.UserFeignClient;
import arquitectura.grupo19.trip_microservice.model.Scooter;
import arquitectura.grupo19.trip_microservice.model.Stop;
import arquitectura.grupo19.trip_microservice.model.User;
import arquitectura.grupo19.trip_microservice.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    private static final double TOLERANCE = 0.0001; // Define el margen de tolerancia para las paradas

    private final TripRepository tripRepository;
    private final UserFeignClient userFeignClient;
    private final ScooterFeignClient scooterFeignClient;
    private final TripBillingService tripBillingService;
    private final StopFeignClient stopFeignClient;

    @Autowired
    public TripService(TripRepository tripRepository, UserFeignClient userFeignClient, ScooterFeignClient scooterFeignClient, TripBillingService tripBillingService, StopFeignClient stopFeignClient) {
        this.tripRepository = tripRepository;
        this.userFeignClient = userFeignClient;
        this.scooterFeignClient = scooterFeignClient;
        this.tripBillingService = tripBillingService;
        this.stopFeignClient = stopFeignClient;
    }
    @Transactional(readOnly = true)
    public List<TripDto> getTrips(){
        return tripRepository.findAll()
                .stream().map(TripDto::new).toList();
    }
    @Transactional
    public TripResponseDto createTrip(Long userId, Long scooterId) {
        TripResponseDto responseDto = new TripResponseDto();
        Scooter scooter = scooterFeignClient.getScooterById(scooterId);

        if(!validateUser(userId, responseDto)) return responseDto;
        if (!validateScooter(scooterId, responseDto)) return responseDto;
        if (!validateUserActiveTrip(userId, responseDto)) return responseDto;
        if (!tripBillingService.hasSufficientBalance(userId)) {
            responseDto.setMessage("No hay suficiente saldo en las cuentas asociadas.");
            responseDto.setSuccess(false);
            return responseDto;
        }
        Trip trip = new Trip();
        trip.setUserId(userId);
        trip.setScooterId(scooterId);
        trip.setStartDateTime(LocalDateTime.now());
        trip.setStartLatitude(scooter.getLatitude());
        trip.setStartLongitude(scooter.getLongitude());

        tripRepository.save(trip);

        tripBillingService.startBilling(trip);

        return mapToTripResponseDto(trip, "Viaje creado exitosamente");
    }


    @Transactional
    public TripResponseDto endTrip(long tripId, long scooterId) {
        TripResponseDto responseDto = new TripResponseDto();
        Optional<Trip> tripOptional = tripRepository.findById(tripId);

        if(tripOptional.isEmpty()){
            responseDto.setMessage("El viaje no existe");
            responseDto.setSuccess(false);
            return responseDto;
        }

        Trip trip = tripOptional.get();

        trip.setEndLatitude(scooterFeignClient.getScooterById(scooterId).getLatitude());
        trip.setEndLongitude(scooterFeignClient.getScooterById(scooterId).getLongitude());

        // 1. Validar si el monopatín está en una parada permitida
        List<Stop> stops = stopFeignClient.getAllStops();
        boolean atPermittedStop = stops.stream()
                .anyMatch(stop -> isWithinRange(
                        stop.getLatitude(), stop.getLongitude(),
                        trip.getEndLatitude(), trip.getEndLongitude()
                ));

        if (!atPermittedStop) {
            responseDto.setMessage("El monopatín debe estar en una parada permitida para finalizar el viaje.");
            responseDto.setSuccess(false);
            return responseDto;
        }

        // 2. Registrar la fecha y hora de finalización y calcular los kilómetros recorridos
        trip.setEndDateTime(LocalDateTime.now());
        trip.setKmTraveled(calculateKilometers(trip.getStartLatitude(), trip.getStartLongitude(), trip.getEndLatitude(), trip.getEndLongitude()));

        // 3. Obtener el monopatín y actualizar los datos acumulativos
        long scooter = trip.getScooterId();
        Duration duration = Duration.between(trip.getStartDateTime(), trip.getEndDateTime());
        int tripDuration = (int) duration.toMinutes();
        scooterFeignClient.addTimeOfUse(scooter, tripDuration);

        // 4. Si se aplicó un recargo, revertirlo
        if (trip.isAdditionalChargeApplied()) {
            trip.setAdditionalChargeApplied(false);
        }
        // Guardar el viaje actualizado
        tripRepository.save(trip);

        return mapToTripResponseDto(trip, "Finalizó el viaje");
    }

    @Transactional
    public TripResponseDto updateTripWithAdditionalCharge(long tripId){
        TripResponseDto responseDto = new TripResponseDto();

        Optional<Trip> trip = tripRepository.findById(tripId);
        if(trip.isEmpty()){
            responseDto.setMessage("El viaje no existe.");
            responseDto.setSuccess(false);
            return responseDto;
        }
        // Incrementar precio del viaje
        trip.get().setAdditionalChargeApplied(true);

        return mapToTripResponseDto(trip.orElse(null), "Viaje actualizado exitosamente");
    }

    @Transactional
    public List<Long> findScootersWithMinTrips(int year, int minTrips) {
        return tripRepository.findScootersWithMinTrips(year, minTrips);
    }

    /**
     * Método para obtener el total facturado en un rango de meses de un año
     */
    @Transactional
    public double getTotalBilledInPeriod(int year, int startMonth, int endMonth) {
        List<Trip> trips = tripRepository.findTripsInPeriod(year, startMonth, endMonth);

        return trips.stream()
                .mapToDouble(Trip::getCost) // suponiendo que cada viaje tiene un campo de costo
                .sum();
    }


    /***************************** VALIDACIONES *****************************/
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
        if (scooter == null || !scooterFeignClient.isAvailable(scooterId)) {
            responseDto.setMessage("El monopatín no está disponible.");
            responseDto.setSuccess(false);
            return false;
        }
        return true;
    }

    // Validar si el usuario ya tiene un viaje activo
    private boolean validateUserActiveTrip(long userId, TripResponseDto responseDto) {
        boolean hasActiveTrip = tripRepository.existsByUserIdAndEndDateTimeIsNull(userId);
        if (hasActiveTrip) {
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

    private double calculateKilometers(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
        final int EARTH_RADIUS_KM = 6371; // Radio de la Tierra en kilómetros

        // Convertir las coordenadas de grados a radianes
        double startLatRad = Math.toRadians(startLatitude);
        double startLonRad = Math.toRadians(startLongitude);
        double endLatRad = Math.toRadians(endLatitude);
        double endLonRad = Math.toRadians(endLongitude);

        // Aplicar la fórmula de Haversine
        double deltaLat = endLatRad - startLatRad;
        double deltaLon = endLonRad - startLonRad;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(startLatRad) * Math.cos(endLatRad) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calcular la distancia
        return EARTH_RADIUS_KM * c;
    }

    private TripResponseDto mapToTripResponseDto(Trip trip, String message) {
        TripResponseDto responseDto = new TripResponseDto();
        responseDto.setScooterId(trip.getScooterId());
        responseDto.setUserId(trip.getUserId());
        responseDto.setStartDateTime(trip.getStartDateTime());
        responseDto.setEndDateTime(trip.getEndDateTime());
        responseDto.setStartLatitude(trip.getStartLatitude());
        responseDto.setStartLongitude(trip.getStartLongitude());
        responseDto.setEndLatitude(trip.getEndLatitude());
        responseDto.setEndLongitude(trip.getEndLongitude());
        responseDto.setKmRecorridos(trip.getKmTraveled());
        responseDto.setMessage(message);
        responseDto.setSuccess(true);
        return responseDto;
    }

    private LocalDateTime parseDate(String date,String time) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try {
            LocalDate localDate = LocalDate.parse(date, dateFormatter);

            LocalTime localTime = LocalTime.parse(time, timeFormatter);

            return LocalDateTime.of(localDate, localTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("La fecha u hora proporcionada no tiene el formato esperado: dd/MM/yyyy y HH:mm", e);
        }
    }

    private boolean isWithinRange(double lat1, double lon1, double lat2, double lon2) {
        return Math.abs(lat1 - lat2) <= TOLERANCE && Math.abs(lon1 - lon2) <= TOLERANCE;
    }
}
