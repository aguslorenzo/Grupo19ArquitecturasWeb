package arquitectura.grupo19.admin_microservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scooter {

	private ScooterStatus status = ScooterStatus.AVAILABLE;
    private int kilometers = 0; 
    private double activeTime = 0; 
	
}
