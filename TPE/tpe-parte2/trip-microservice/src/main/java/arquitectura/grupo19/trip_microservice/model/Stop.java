package arquitectura.grupo19.trip_microservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stop {
    private long id;
    private String directionDescription;
    private Locale location;
}
