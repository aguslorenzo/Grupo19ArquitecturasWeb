package arquitectura.grupo19.report_microservice.services;

import arquitectura.grupo19.report_microservice.dto.ReportDto;
import arquitectura.grupo19.report_microservice.entities.Report;
import arquitectura.grupo19.report_microservice.exceptions.NotFoundException;
import arquitectura.grupo19.report_microservice.feignClient.ScooterFeignClient;
import arquitectura.grupo19.report_microservice.repositories.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final ScooterFeignClient scooterFeignClient;

    public ReportService(ReportRepository reportRepository, ScooterFeignClient scooterFeignClient) {
        this.reportRepository = reportRepository;
        this.scooterFeignClient = scooterFeignClient;
    }

    public List<ReportDto> getReports(){
        return reportRepository.findAll()
                .stream().map(ReportDto::new).toList();
    }

    public ReportDto getReportByScooter(long scooterId){
        return Optional.ofNullable(reportRepository.findByScooterId(scooterId))
                .map(ReportDto::new)
                .orElseThrow(()->new NotFoundException("Report", scooterId));
    }

    public Double getReportKilometersByScooter(long id) {
        return scooterFeignClient.getKilometers(id);
    }

    public int getReportTimeWithPausesByScooter(long id){
        return scooterFeignClient.getUsageTime(id);
    }

    public int getReportTimeWithoutPausesByScooter(long id){
        return scooterFeignClient.getActiveTime(id);
    }

    private Report convertDtoToEntity(ReportDto reportDto) {
        Report report = new Report();
        report.setKilometers(reportDto.getKilometers());
        report.setActiveTime(reportDto.getActiveTime());
        report.setInactiveTime(reportDto.getInactiveTime());
        report.setUsageTime(reportDto.getUsageTime());
        return report;
    }

    private ReportDto convertEntityToDto(Report report) {
        ReportDto reportDto = new ReportDto();
        reportDto.setKilometers(report.getKilometers());
        reportDto.setActiveTime(report.getActiveTime());
        reportDto.setInactiveTime(report.getInactiveTime());
        reportDto.setUsageTime(report.getUsageTime());
        return reportDto;
    }
}
