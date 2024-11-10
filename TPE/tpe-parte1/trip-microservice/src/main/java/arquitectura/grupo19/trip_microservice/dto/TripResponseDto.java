package arquitectura.grupo19.trip_microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripResponseDto {
    private Long userId;
    private Long scooterId;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private double kmRecorridos;
    private String message;  // mensaje a enviar
    private boolean success;   // éxito o fracaso del viaje
}
