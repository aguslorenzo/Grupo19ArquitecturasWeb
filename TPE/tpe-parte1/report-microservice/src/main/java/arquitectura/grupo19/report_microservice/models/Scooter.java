package arquitectura.grupo19.report_microservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scooter {
    private long id;
    private int usageTime;
    private int activeTime;
    private int inactiveTime;
}
