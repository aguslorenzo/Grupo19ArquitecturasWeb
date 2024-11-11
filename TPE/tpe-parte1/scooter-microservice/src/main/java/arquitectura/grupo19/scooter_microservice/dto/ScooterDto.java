package arquitectura.grupo19.scooter_microservice.dto;

import arquitectura.grupo19.scooter_microservice.entities.Scooter;
import arquitectura.grupo19.scooter_microservice.entities.ScooterStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScooterDto {

    @NotNull(message = "El estatus es requerido")
    @NotEmpty(message = "El estatus es un campo requerido")
    private ScooterStatus status;

    @NotNull(message = "El valor de kilometros es requerido")
    private int kilometers;

    @NotNull(message = "El valor de tiempo activo es requerido")
    private double activeTime;

    public ScooterDto(Scooter scooter) {
        this.status = scooter.getStatus();
        this.kilometers = scooter.getKilometers();
        this.activeTime = scooter.getActiveTime();
    }
}
