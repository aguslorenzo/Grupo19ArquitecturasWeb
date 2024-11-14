package arquitectura.grupo19.map_microservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScooterLocation {
    @Id
    private Long scooterId;
    private double latitude;
    private double longitude;
    private String state;
}
