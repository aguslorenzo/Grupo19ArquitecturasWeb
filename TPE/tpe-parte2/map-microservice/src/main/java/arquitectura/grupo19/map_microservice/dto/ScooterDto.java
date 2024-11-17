package arquitectura.grupo19.map_microservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

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
    private double activeTime;

    @NotNull
    private double latitude;
    @NotNull
    private double longitude;

}
