package arquitectura.grupo19.scooter_microservice.dto;

import arquitectura.grupo19.scooter_microservice.entities.Scooter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScooterLocationDto {
    @Id
    private Long scooterId;

    private double latitude;
    private double longitude;

    public ScooterLocationDto(Scooter scooter) {
        this.scooterId = scooter.getId();
        this.latitude = scooter.getLatitude();
        this.longitude = scooter.getLongitude();
    }
}
