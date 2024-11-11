package arquitectura.grupo19.trip_microservice.services;

import arquitectura.grupo19.trip_microservice.entities.Trip;
import arquitectura.grupo19.trip_microservice.feignClient.ScooterFeignClient;
import arquitectura.grupo19.trip_microservice.feignClient.UserFeignClient;
import arquitectura.grupo19.trip_microservice.repositories.TripRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TripBillingService {

    private final double FARE_PER_MINUTE = 40;
    private final double FARE_INCREASE_PERCENTAGE = 0.20; // 20% de recargo

    private final TripRepository tripRepository;
    private final UserFeignClient userFeignClient;
    private final ScooterFeignClient scooterFeignClient;

    public TripBillingService(TripRepository tripRepository, UserFeignClient userFeignClient, ScooterFeignClient scooterFeignClient) {
        this.tripRepository = tripRepository;
        this.userFeignClient = userFeignClient;
        this.scooterFeignClient = scooterFeignClient;
    }

    @Scheduled(fixedRate = 60000) // Ejecuta cada minuto
    public void processOngoingTrips() {
        List<Trip> ongoingTrips = tripRepository.findByEndDateTimeIsNull(); // Encuentra todos los viajes en curso
        for (Trip trip : ongoingTrips) {
            handleTripBilling(trip);
        }
    }

    private void handleTripBilling(Trip trip) {
        LocalDateTime now = LocalDateTime.now();
        long minutesElapsed = Duration.between(trip.getLastBilledTime(), now).toMinutes();

        if (minutesElapsed > 0) {
            double costPerMinute = FARE_PER_MINUTE;

            // Verificar si el recargo ha sido aplicado y si debe aplicarse a partir de ahora
            if (trip.isAdditionalChargeApplied()) {
                // Si el recargo ya ha sido aplicado, aumentamos el costo por minuto
                costPerMinute = FARE_PER_MINUTE * (1 + FARE_INCREASE_PERCENTAGE);
            }

            boolean hasSufficientBalance = userFeignClient.hasSufficientBalance(trip.getUserId(), costPerMinute);

            if (hasSufficientBalance) {
                // Descuenta el saldo
                userFeignClient.deductBalance(trip.getUserId(), costPerMinute);
                trip.setLastBilledTime(now); // Actualiza el último momento facturado
                tripRepository.save(trip);
            } else {
                // Notificar al usuario y apagar el monopatín si se quedó sin saldo
                handleInsufficientBalance(trip);
            }
        }
    }

    private void handleInsufficientBalance(Trip trip) {
        // Envía una notificación al usuario sobre saldo insuficiente
        userFeignClient.notifyUser(trip.getUserId(), "Saldo insuficiente. Por favor, recargue o termine el viaje.");

        // Finaliza el viaje automáticamente
        scooterFeignClient.deactivateScooter(trip.getScooterId());
        trip.setEndDateTime(LocalDateTime.now());
        tripRepository.save(trip);
    }

    public void startBilling(Trip trip) {
        handleTripBilling(trip);
    }

    public boolean hasSufficientBalance(long userId) {
        return userFeignClient.hasSufficientBalance(userId, FARE_PER_MINUTE);
    }
}

