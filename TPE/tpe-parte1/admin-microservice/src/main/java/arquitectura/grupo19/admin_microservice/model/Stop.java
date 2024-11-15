package arquitectura.grupo19.admin_microservice.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Stop {
    private long id;
    private String directionDescription;
    private double latitude;
    private double longitude;
}