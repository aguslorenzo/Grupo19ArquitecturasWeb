package arquitectura.grupo19.trip_microservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long scooterId;
    private LocalDateTime startDateTime; //fechaHoraInicio
    private LocalDateTime endDateTime; //fechaHoraFin
    private double kmTraveled; //kmRecorridos
    private Locale initialStop;
    private Locale endLocation;
    private LocalDateTime lastBilledTime;
    private boolean additionalChargeApplied; // Indica si el recargo se ha aplicado
    private LocalDateTime additionalChargeStartTime; // El momento en que comenzó el recargo
}
