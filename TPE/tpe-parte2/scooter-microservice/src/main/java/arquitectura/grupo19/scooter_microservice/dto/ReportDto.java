package arquitectura.grupo19.scooter_microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {

    private long scooterId;
    private double kilometers;
    private double usageTime;
    private boolean includePauseTimes;

}
