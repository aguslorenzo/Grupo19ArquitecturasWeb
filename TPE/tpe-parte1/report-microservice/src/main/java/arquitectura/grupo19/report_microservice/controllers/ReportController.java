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

    /**
     * a) Como encargado de mantenimiento quiero poder generar un reporte de uso de monopatines por
     * kilómetros para establecer si un monopatín requiere de mantenimiento. Este reporte debe poder
     * configurarse para incluir (o no) los tiempos de pausa.
     */
    @GetMapping("/scooter/{id}/usage-report")
    public ReportDto getUsageReportByScooter(@PathVariable Long id, @RequestParam boolean includePauseTimes) {
        return reportService.generateUsageReportByScooter(id, includePauseTimes);
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
