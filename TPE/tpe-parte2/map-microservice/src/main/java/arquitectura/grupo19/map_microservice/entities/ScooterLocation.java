package arquitectura.grupo19.map_microservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor // Crea un constructor sin argumentos
@AllArgsConstructor
public class ScooterLocation {
    @Id
    private Long scooterId;

    private String gpsLocation;  // Formato "latitud,longitud"

    private LocalDateTime lastUpdated;
}
