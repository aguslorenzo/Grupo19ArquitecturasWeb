package arquitectura.grupo19.user_microservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor // Crea un constructor sin argumentos
@AllArgsConstructor
public class ScooterLocation {
    @Id
    private Long scooterId;
    private double latitude;
    private double longitude;
    private String state;
}
