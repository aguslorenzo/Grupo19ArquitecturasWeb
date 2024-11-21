package arquitectura.grupo19.trip_microservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Stop {
    @Id
    private String id;
    private String directionDescription;
    private double latitude;
    private double longitude;
}
