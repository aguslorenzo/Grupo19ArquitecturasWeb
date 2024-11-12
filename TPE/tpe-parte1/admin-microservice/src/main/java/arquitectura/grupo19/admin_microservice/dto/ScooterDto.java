package arquitectura.grupo19.scooter_microservice.dto;

import arquitectura.grupo19.scooter_microservice.entities.Scooter;
import arquitectura.grupo19.scooter_microservice.entities.ScooterState;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScooterDto {

    @NotNull(message = "El estado es requerido")
    @NotEmpty(message = "El estado es un campo requerido")
    private ScooterState state;

    @NotNull(message = "El valor de kilometros es requerido")
    private int kilometers;

    @NotNull(message = "El valor de tiempo activo es requerido")
    private double activeTime;

    public ScooterDto(Scooter scooter) {
        this.state = scooter.getState();
        this.kilometers = scooter.getKilometers();
        this.activeTime = scooter.getActiveTime();
    }
}
