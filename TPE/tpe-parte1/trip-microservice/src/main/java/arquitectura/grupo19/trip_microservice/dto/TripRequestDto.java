package arquitectura.grupo19.trip_microservice.dto;

import lombok.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripRequestDto {
    @NotEmpty(message = "El campo id del usuario no puede estar vacío")
    @NotNull( message = "El userId es un campo obligatorio.")
    private long userId;

    @NotNull( message = "El scooterId es un campo obligatorio.")
    private long scooterId;

    @NotNull( message = "La fecha de inicio es un campo obligatorio.")
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private double kmTraveled;
}
