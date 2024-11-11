package arquitectura.grupo19.scooter_microservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Locale;

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

    @Column(nullable=false)
    private int kilometers;
    @Column(nullable = false)
    private double activeTime;
    @Column(nullable = false)
    private Locale gpsLocation;
    private boolean isActive;
    private boolean isPaused;
    private LocalDateTime pauseStartTime;
    private Long currentTripId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScooterState state;//TODO could be > enum AVAILABLE - IN_MAINTENANCE
 
}
