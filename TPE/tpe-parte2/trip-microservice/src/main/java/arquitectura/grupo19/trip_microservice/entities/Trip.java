package arquitectura.grupo19.trip_microservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "trip")
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
    private double startLatitude;
    private double startLongitude;
    private double endLatitude;
    private double endLongitude;
    private LocalDateTime lastBilledTime;
    private boolean additionalChargeApplied = false; // Indica si el recargo se ha aplicado
    private LocalDateTime additionalChargeStartTime; // El momento en que comenzó el recargo
    private double cost = 0.0;
}
