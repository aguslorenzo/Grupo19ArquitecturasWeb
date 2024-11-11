package arquitectura.grupo19.report_microservice.services;

import arquitectura.grupo19.report_microservice.dto.ReportDto;
import arquitectura.grupo19.report_microservice.entities.Report;
import arquitectura.grupo19.report_microservice.exceptions.NotFoundException;
import arquitectura.grupo19.report_microservice.repositories.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Transactional(readOnly = true)
    public List<ReportDto> getReports(){
        return reportRepository.findAll()
                .stream().map(ReportDto::new).toList();
    }

    @Transactional(readOnly = true)
    public ReportDto getReportByScooter(Long scooterId){
        return reportRepository.findByScooterId(scooterId)
                .map(ReportDto::new)
                .orElseThrow(()->new NotFoundException("Report", scooterId));
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
