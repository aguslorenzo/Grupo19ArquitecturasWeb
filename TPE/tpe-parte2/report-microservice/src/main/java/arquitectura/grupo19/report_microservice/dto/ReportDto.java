package arquitectura.grupo19.report_microservice.dto;

import arquitectura.grupo19.report_microservice.entities.Report;
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

    public ReportDto(Report report){
        this.scooterId = report.getScooterId();
        this.kilometers = report.getKilometers();
        this.usageTime = report.getUsageTime();
        this.includePauseTimes = report.isIncludePauseTimes();
    }
}
