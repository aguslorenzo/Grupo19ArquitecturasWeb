package arquitectura.grupo19.stop_microservice.dto;

import arquitectura.grupo19.stop_microservice.entities.Stop;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StopDto {

    @NotNull(message = "La dirección es requerida")
    @NotEmpty(message = "La dirección es un campo requerido")
    private String directionDescription;

    private double latitude;
    private double longitude;

    public StopDto(Stop stop) {
        this.directionDescription = stop.getDirectionDescription();
        this.latitude = stop.getLatitude();
        this.longitude = stop.getLongitude();
    }
}
