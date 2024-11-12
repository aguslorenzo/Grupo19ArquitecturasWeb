package arquitectura.grupo19.scooter_microservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Scooter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String gpsLocation;
    private int kilometers;
    private Duration usageTime;
    private Duration activeTime;
    private Duration inactiveTime;
    private boolean isActive;
    private boolean isPaused;
    private LocalDateTime pauseStartTime;
    private Long currentTripId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScooterState state;
 
}
