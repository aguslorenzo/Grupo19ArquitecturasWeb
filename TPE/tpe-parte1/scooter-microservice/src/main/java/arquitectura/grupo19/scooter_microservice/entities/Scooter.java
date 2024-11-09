package arquitectura.grupo19.scooter_microservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor // Crea un constructor sin argumentos
@AllArgsConstructor
public class Scooter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String location; //esto puede ser un array de double, un String, o una clase personalizada Location
    @Column(nullable = false)
    private String status;
    @Column(nullable=false)
    private int kilometers;
    @Column(nullable = false)
    private double activeTime;
    //@OneToMany(mappedBy = "scooter"
    //private List<Trip> trips;

}
