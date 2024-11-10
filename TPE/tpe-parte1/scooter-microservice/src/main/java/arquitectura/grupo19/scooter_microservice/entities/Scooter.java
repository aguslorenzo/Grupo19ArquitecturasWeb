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
    @Column(nullable = false)
    private String status;//TODO could be > enum AVAILABLE - IN_MAINTENANCE
    @Column(nullable=false)
    private int kilometers;
    @Column(nullable = false)
    private double activeTime;
    //@OneToMany(mappedBy = "scooter"
    //private List<Trip> trips;
}
