package arquitectura.grupo19.stop_microservice.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stops")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Stop {
    @Id
    private String id;
    private String directionDescription;
    private double latitude;
    private double longitude;
}
