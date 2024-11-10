package arquitectura.grupo19.trip_microservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scooter {
    private long id;
    private boolean isAvailable; // TODO poner en true or false dependiendo de si está o no en mantenimiento o fuera de servicio.
}
