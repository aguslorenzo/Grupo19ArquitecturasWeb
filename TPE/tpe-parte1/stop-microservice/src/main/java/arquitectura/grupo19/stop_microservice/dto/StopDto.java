package arquitectura.grupo19.stop_microservice.dto;

import arquitectura.grupo19.stop_microservice.entities.Stop;
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

    @NotNull(message = "El valor de xAxis es requerido")
    private Double xAxis;

    @NotNull(message = "El valor de yAxis es requerido")
    private Double yAxis;

    private Long scooterId;

    public StopDto(Stop stop) {
        this.directionDescription = stop.getDirectionDescription();
        this.xAxis = stop.getXAxis();
        this.yAxis = stop.getYAxis();
        this.scooterId = stop.getScooterId();
    }
}
