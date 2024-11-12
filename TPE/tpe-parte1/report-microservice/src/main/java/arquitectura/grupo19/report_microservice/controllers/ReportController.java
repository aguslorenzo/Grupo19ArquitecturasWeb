package arquitectura.grupo19.report_microservice.controllers;

import arquitectura.grupo19.report_microservice.dto.ReportDto;
import arquitectura.grupo19.report_microservice.services.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reports")
public class ReportController {

    public final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReportDto> getReports() {
        return reportService.getReports();
    }

    @GetMapping("/scooter/{scooterId}")
    public ReportDto getReportByScooter(@PathVariable Long scooterId) {
        return reportService.getReportByScooter(scooterId);
    }

    @GetMapping("/scooter/{id}/kilometers")
    public Double getReportKilometersByScooter(@PathVariable Long id) {
        return reportService.getReportKilometersByScooter(id);
    }

    @GetMapping("/scooter/{id}/timewithpauses")
    public int getReportTimeWithPausesByScooter(@PathVariable Long id) {
        return reportService.getReportTimeWithPausesByScooter(id);
    }

    @GetMapping("/scooter/{id}/timewithoutpauses")
    public int getReportTimeWithoutPausesByScooter(@PathVariable Long id) {
        return reportService.getReportTimeWithoutPausesByScooter(id);
    }
}
