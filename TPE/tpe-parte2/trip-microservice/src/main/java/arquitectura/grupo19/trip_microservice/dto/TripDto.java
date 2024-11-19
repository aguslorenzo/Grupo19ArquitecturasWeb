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
    private double startLatitude;
    private double startLongitude;
    private double endLatitude;
    private double endLongitude;

    public TripDto(Trip trip) {
        this.id = trip.getId();
        this.userId = trip.getUserId();
        this.scooterId = trip.getScooterId();
        this.startLatitude = trip.getStartLatitude();
        this.startLongitude = trip.getStartLongitude();
    }
}
