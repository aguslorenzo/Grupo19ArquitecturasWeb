package arquitectura.grupo19.scooter_microservice.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private long id;
    private long scooterId;
    private double kilometers;
    private double usageTime;
    private boolean includePauseTimes;
}
