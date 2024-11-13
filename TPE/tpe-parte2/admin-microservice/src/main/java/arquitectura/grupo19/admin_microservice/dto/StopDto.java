package arquitectura.grupo19.admin_microservice.dto;

import arquitectura.grupo19.admin_microservice.model.Stop;
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

    private String location;

    public StopDto(Stop stop) {
        this.directionDescription = stop.getDirectionDescription();
        this.location = stop.getLocation();
    }
}
