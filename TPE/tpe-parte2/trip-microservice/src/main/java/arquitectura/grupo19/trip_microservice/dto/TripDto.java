package arquitectura.grupo19.trip_microservice.dto;

import arquitectura.grupo19.trip_microservice.entities.Trip;
import lombok.*;

import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripDto {
    private Long id;
    private Long userId;
    private Long scooterId;

    public TripDto(Trip trip) {
        this.id = trip.getId();
        this.userId = trip.getUserId();
        this.scooterId = trip.getScooterId();
    }
}
