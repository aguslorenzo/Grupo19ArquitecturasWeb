package arquitectura.grupo19.scooter_microservice.services;


import arquitectura.grupo19.report_microservice.dto.ReportDto;
import arquitectura.grupo19.scooter_microservice.entities.Scooter;
import arquitectura.grupo19.scooter_microservice.entities.ScooterState;
import arquitectura.grupo19.scooter_microservice.feignClient.ReportFeignClient;
import arquitectura.grupo19.scooter_microservice.feignClient.TripFeignClient;
import arquitectura.grupo19.scooter_microservice.repositories.ScooterRepository;
import arquitectura.grupo19.scooter_microservice.services.constants.MaintenanceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static arquitectura.grupo19.scooter_microservice.services.constants.MaintenanceConfig.MAX_KM_TRAVELED;
import static arquitectura.grupo19.scooter_microservice.services.constants.MaintenanceConfig.MAX_USAGE_TIME_MINUTES;

@Component
public class ScooterScheduler {

    @Autowired
    private ScooterService scooterService;

    @Autowired
    private ScooterRepository scooterRepository;

    @Autowired
    private TripFeignClient tripFeignClient;
    @Autowired
    private ReportFeignClient reportFeignClient;

    // Esta tarea se ejecutará cada minuto
    @Scheduled(fixedRate = 60000)
    public void checkPauses() {
        List<Scooter> pausedScooters = scooterRepository.findAllByState(ScooterState.PAUSED);

        for (Scooter scooter : pausedScooters) {
            // Verifica si la pausa ha superado los 15 minutos
            if (scooter.getPauseStartTime() != null) {
                long minutesPaused = Duration.between(scooter.getPauseStartTime(), LocalDateTime.now()).toMinutes();
                if (minutesPaused > 15) {
                    // Si ha pasado más de 15 minutos, se vuelve a poner en uso el monopatín
                    scooterService.restartScooter(scooter.getId());

                    // Obtener el viaje asociado
                    Long currentTripId = scooter.getCurrentTripId();

                    if (currentTripId != 0) {
                        tripFeignClient.updateTripWithAdditionalCharge(currentTripId);
                    }
                    System.out.println("Scooter " + scooter.getId() + " ha estado pausado por más de 15 minutos, tarifa aumentada.");
                }
            }
        }
    }

    // AUTOMATIZACIÓN DE MANTENIMIENTO
    @Scheduled(cron = "0 0 2 * * ?") // Ejecutar todos los días a las 2:00 AM
    public void scheduledMaintenanceCheck() {
        List<Scooter> scooters = scooterRepository.findAll();

        for (Scooter scooter : scooters) {
            if (checkMaintenanceStatus(scooter.getId())) {
                scooter.setState(ScooterState.IN_MAINTENANCE);
                scooterRepository.save(scooter);
                // Mandar mensaje al encargado de mantenimiento
            }
        }
    }

    public boolean checkMaintenanceStatus(Long scooterId) {
        ReportDto report = reportFeignClient.getUsageReportByScooter(scooterId, true);

        boolean needsMaintenance = report.getKilometers() >= MAX_KM_TRAVELED ||
                report.getUsageTime() >= MAX_USAGE_TIME_MINUTES;

        return needsMaintenance;
    }
}
