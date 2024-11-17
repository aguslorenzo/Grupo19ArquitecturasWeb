package arquitectura.grupo19.user_microservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScooterLocation {
    @Id
    private Long id;
    private double latitude;
    private double longitude;
    private String state;
}
