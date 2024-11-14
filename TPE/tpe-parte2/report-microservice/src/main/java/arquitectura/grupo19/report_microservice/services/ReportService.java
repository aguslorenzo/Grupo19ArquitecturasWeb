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

    public ReportDto generateUsageReportByScooter(Long scooterId, boolean includePauseTimes) {
        Double kilometers = scooterFeignClient.getKilometers(scooterId);
        int usageTime = includePauseTimes
                ? getReportTimeWithPausesByScooter(scooterId)
                : getReportTimeWithoutPausesByScooter(scooterId);

        // Crear el objeto ReportDto con la información obtenida
        ReportDto report = new ReportDto();
        report.setScooterId(scooterId);
        report.setKilometers(kilometers);
        report.setUsageTime(usageTime);
        report.setIncludePauseTimes(includePauseTimes);

        return report;
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
        report.setUsageTime(reportDto.getUsageTime());
        report.setIncludePauseTimes(reportDto.isIncludePauseTimes());
        /*report.setActiveTime(reportDto.getActiveTime());
        report.setInactiveTime(reportDto.getInactiveTime());*/
        return report;
    }

    private ReportDto convertEntityToDto(Report report) {
        ReportDto reportDto = new ReportDto();
        reportDto.setKilometers(report.getKilometers());
        reportDto.setUsageTime(report.getUsageTime());
        reportDto.setIncludePauseTimes(report.isIncludePauseTimes());
        /*reportDto.setActiveTime(report.getActiveTime());
        reportDto.setInactiveTime(report.getInactiveTime());*/
        return reportDto;
    }
}
