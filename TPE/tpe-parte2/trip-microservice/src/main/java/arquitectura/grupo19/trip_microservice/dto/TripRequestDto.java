package arquitectura.grupo19.trip_microservice.dto;

import lombok.*;

import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripRequestDto {
    @NotNull( message = "El userId es un campo obligatorio.")
    private Long userId;

    @NotNull( message = "El scooterId es un campo obligatorio.")
    private Long scooterId;

    @NotNull( message = "La fecha de inicio es un campo obligatorio.")
    private String startDateTime;

    @NotNull( message = "La hora de inicio es un campo obligatorio.")
    private String startTime;

}
