package arquitectura.grupo19.stop_microservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor // Crea un constructor sin argumentos
@AllArgsConstructor
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String directionDescription;
    @Column(nullable = false)
    private Double xAxis;
    @Column(nullable = false)
    private Double yAxis;
    @Column(nullable = true)
    private Long scooterId;



}