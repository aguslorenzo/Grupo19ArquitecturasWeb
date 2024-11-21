package arquitectura.grupo19.admin_microservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stop {
    private String id;
    private String directionDescription;
    private double latitude;
    private double longitude;
}
