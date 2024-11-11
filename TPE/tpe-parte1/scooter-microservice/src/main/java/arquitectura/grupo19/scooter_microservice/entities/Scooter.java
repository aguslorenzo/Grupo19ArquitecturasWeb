package arquitectura.grupo19.scooter_microservice.entities;

import jakarta.persistence.*;
import lombok.*;

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
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScooterStatus status;//TODO could be > enum AVAILABLE - IN_MAINTENANCE
    @Column(nullable=false)
    private int kilometers;
    @Column(nullable = false)
    private double activeTime;
    
 
}
