package arquitectura.grupo19.admin_microservice.dto;


import arquitectura.grupo19.admin_microservice.model.ScooterState;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScooterDto {

    @NotNull(message = "El estado es requerido")
    private ScooterState state;

    @NotNull(message = "El valor de kilometros es requerido")
    private int kilometers;

    @NotNull(message = "El valor de tiempo activo es requerido")
    private double activeTime;

    @NotNull
    private String gpsLocation;

}
