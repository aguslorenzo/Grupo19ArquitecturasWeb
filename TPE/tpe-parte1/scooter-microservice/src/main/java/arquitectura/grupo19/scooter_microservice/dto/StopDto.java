package arquitectura.grupo19.scooter_microservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StopDto {

    @NotNull(message = "La dirección es requerida")
    @NotEmpty(message = "La dirección es un campo requerido")
    private String directionDescription;

    private double latitude;
    private double longitude;

}
