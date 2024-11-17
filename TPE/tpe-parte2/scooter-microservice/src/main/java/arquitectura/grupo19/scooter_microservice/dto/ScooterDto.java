package arquitectura.grupo19.scooter_microservice.dto;

import arquitectura.grupo19.scooter_microservice.entities.Scooter;
import arquitectura.grupo19.scooter_microservice.entities.ScooterState;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScooterDto {
    private Long id;

    @NotNull(message = "El estado es requerido")
    private ScooterState state;

    @NotNull(message = "El valor de kilometros es requerido")
    private int kilometers;

    @NotNull(message = "El valor de tiempo activo es requerido")
    private int activeTime;

    private double latitude;
    private double longitude;

    public ScooterDto(Scooter scooter) {
        this.state = scooter.getState();
        this.kilometers = scooter.getKilometers();
        this.activeTime = scooter.getActiveTime();
        this.latitude = scooter.getLatitude();
        this.longitude = scooter.getLongitude();
    }
}
