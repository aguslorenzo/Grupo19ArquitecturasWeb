package arquitectura.grupo19.trip_microservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    // TODO origen parada
    // TODO destino parada
    private LocalDateTime lastBilledTime;
}
