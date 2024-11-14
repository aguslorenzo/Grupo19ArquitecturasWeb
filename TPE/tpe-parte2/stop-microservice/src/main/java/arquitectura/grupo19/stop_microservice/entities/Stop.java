package arquitectura.grupo19.stop_microservice.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.xml.stream.Location;
import java.util.Locale;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String directionDescription;
    @Column(nullable = false)
    private double latitude;
    @Column(nullable = false)
    private double longitude;
}