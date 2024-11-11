package arquitectura.grupo19.stop_microservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scooter {

	private long id;
	private String location;
	private String status;
	private int kilometers;
	private double activeTime;
	
}
