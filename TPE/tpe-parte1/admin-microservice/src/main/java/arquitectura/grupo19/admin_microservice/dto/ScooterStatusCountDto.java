package arquitectura.grupo19.admin_microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScooterStatusCountDto {
    private int inUse;
    private int inMaintenance;
}
