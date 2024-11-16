package arquitectura.grupo19.scooter_microservice.services;

import arquitectura.grupo19.scooter_microservice.dto.ScooterDto;
import arquitectura.grupo19.scooter_microservice.dto.ScooterStatusCountDto;
import arquitectura.grupo19.scooter_microservice.dto.StopDto;
import arquitectura.grupo19.scooter_microservice.entities.Scooter;
import arquitectura.grupo19.scooter_microservice.entities.ScooterState;
import arquitectura.grupo19.scooter_microservice.exceptions.NotFoundException;
import arquitectura.grupo19.scooter_microservice.feignClient.ReportFeignClient;
import arquitectura.grupo19.scooter_microservice.feignClient.StopFeignClient;
import arquitectura.grupo19.scooter_microservice.feignClient.TripFeignClient;
import arquitectura.grupo19.scooter_microservice.repositories.ScooterRepository;

import arquitectura.grupo19.scooter_microservice.dto.ReportDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static arquitectura.grupo19.scooter_microservice.services.constants.MaintenanceConfig.MAX_KM_TRAVELED;
import static arquitectura.grupo19.scooter_microservice.services.constants.MaintenanceConfig.MAX_USAGE_TIME_MINUTES;

@Service
public class ScooterService {

    private final ScooterRepository scooterRepository;
    private final ReportFeignClient reportFeignClient;
    private final TripFeignClient tripFeignClient;
    private final StopFeignClient stopFeignClient;

    public ScooterService(ScooterRepository scooterRepository, ReportFeignClient reportFeignClient, TripFeignClient tripFeignClient, StopFeignClient stopFeignClient) {
        this.scooterRepository = scooterRepository;
        this.reportFeignClient = reportFeignClient;
        this.tripFeignClient = tripFeignClient;
        this.stopFeignClient = stopFeignClient;
    }

    @Transactional
    public ScooterDto saveScooter(ScooterDto scooterDto){
        // Convertir el DTO a entidad Scooter
    	Scooter scooter = convertDtoToEntity(scooterDto);

        // Si pasa las validaciones, guardar parada en base de datos
        Scooter save = scooterRepository.save(scooter);

        // Retornar DTO
        return convertEntityToDto(save);
    }

    @Transactional(readOnly = true) // Para que no guarde el estado y tengamos un mejor rendimiento de la consulta.
    public ScooterDto getScooterById(Long id){
        return scooterRepository.findById(id)
                .map(ScooterDto::new)
                .orElseThrow(()->new NotFoundException("Scooter", id));
    }

    @Transactional(readOnly = true)
    public List<ScooterDto> getScooters(){
        return scooterRepository.findAll()
                .stream().map(ScooterDto::new).toList();
    }

    public void updateScooter(Long id, ScooterDto scooterDto){
        // Buscar scooter por id
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Scooter", id));

        // Actualizar los campos de scooter con los datos nuevos
        scooter.setState(scooterDto.getState());
        scooter.setKilometers(scooterDto.getKilometers());
        //scooter.setActiveTime(scooterDto.getActiveTime());

        // Guardar los cambios
        scooterRepository.save(scooter);
    }

    public ScooterDto deleteScooter(Long id){
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Scooter", id));
        scooterRepository.delete(scooter);
        return convertEntityToDto(scooter);
    }
    
    //SERVICIOS DE CONSULTAS DE MONOPATINES**********************************************************************

	public void putScooterOnMaintenance(Long id) {
		 // Buscar scooter por id
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Scooter", id));
        scooter.setState(ScooterState.IN_MAINTENANCE); //cambiar estado
        scooterRepository.save(scooter); //guardar cambios
	}
	
	public void putScooterAvailable(Long id) {
		 // Buscar scooter por id
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Scooter", id));
        scooter.setState(ScooterState.AVAILABLE); //cambiar estado
        scooterRepository.save(scooter); //guardar cambios
	}

    // Encender el monopatín
    public ScooterDto activateScooter(Long scooterId, Long tripId) {
        Scooter scooter = scooterRepository.findById(scooterId).orElseThrow(() -> new IllegalArgumentException("Scooter not found"));

        // Verificar si el monopatín está en mantenimiento
        if (scooter.getState() == ScooterState.IN_MAINTENANCE) {
            throw new IllegalStateException("Scooter is in maintenance and cannot be activated");
        }

        if (!scooter.isActive()) {
            scooter.setActive(true);
            scooter.setState(ScooterState.IN_USE);
            scooter.setCurrentTripId(tripId);
            scooterRepository.save(scooter);
        }

        return convertEntityToDto(scooter);
    }

    // Apagar el monopatín
    public ScooterDto deactivateScooter(Long scooterId) {
        Scooter scooter = scooterRepository.findById(scooterId).orElseThrow(() -> new IllegalArgumentException("Scooter not found"));
        if (scooter.isActive()) {
            scooter.setActive(false);
            scooter.setState(ScooterState.INACTIVE);
            scooterRepository.save(scooter);
        }
        return convertEntityToDto(scooter);
    }

    // Pausar el monopatín
    public ScooterDto pauseScooter(Long scooterId) {
        Scooter scooter = scooterRepository.findById(scooterId).orElseThrow(() -> new IllegalArgumentException("Scooter not found"));
        if (scooter.isActive() && !scooter.isPaused()) {
            scooter.setPaused(true);
            scooter.setPauseStartTime(LocalDateTime.now());
            scooter.setState(ScooterState.PAUSED);
            scooterRepository.save(scooter);
        }
        return convertEntityToDto(scooter);
    }

    // Reanudar el monopatín después de pausa
    public ScooterDto restartScooter(Long scooterId) {
        Scooter scooter = scooterRepository.findById(scooterId).orElseThrow(() -> new IllegalArgumentException("Scooter not found"));
        if (scooter.isPaused()) {
            scooter.setPaused(false);
            scooter.setState(ScooterState.IN_USE);
            scooterRepository.save(scooter);
        }
        return convertEntityToDto(scooter);
    }

    // Comprobar si el monopatín está en una ubicación permitida
    public boolean checkIfScooterIsInAllowedLocation(Long scooterId) {
        Scooter scooter = scooterRepository.findById(scooterId)
                .orElseThrow(() -> new IllegalArgumentException("Scooter not found"));

        // Obtener todas las ubicaciones permitidas de stops
        List<StopDto> allowedStops = stopFeignClient.getAllStops();

        // Verificar si la ubicación del monopatín coincide con alguna de las ubicaciones de stops permitidos
        return allowedStops.stream()
                .anyMatch(stop -> stop.getLatitude() == scooter.getLatitude() && stop.getLongitude() == scooter.getLongitude());
    }

    // Comprobar si el monopatín está disponible
    public boolean isAvailable(Long scooterId) {
        Scooter scooter = scooterRepository.findById(scooterId)
                .orElseThrow(() -> new IllegalArgumentException("Scooter not found"));
        return scooter.getState() == ScooterState.AVAILABLE;
    }

    // Agregar tiempo de uso y si llegó al tiempo y kms max, poner en MANTENIMIENTO
    public void addTimeOfUse(long id, int time){
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Scooter not found"));
        int actualTime = scooter.getUsageTime() + time;
        if(actualTime >= MAX_USAGE_TIME_MINUTES && scooter.getKilometers() == MAX_KM_TRAVELED){
            scooter.setState(ScooterState.IN_MAINTENANCE);
        }
        scooter.setUsageTime(actualTime);
        scooterRepository.save(scooter);
    }

    // Obtener tiempo acumulado de uso
    public int getUsageTime(long id){
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Scooter not found"));
        return scooter.getUsageTime();
    }

    // Obtener kilometros recorridos
    public double getKilometers(long id){
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Scooter not found"));
        return scooter.getKilometers();
    }

    public int getActiveTime(long id){
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Scooter not found"));
        return scooter.getActiveTime();
    }

    public boolean checkMaintenanceStatus(Long scooterId) {
        // Obtener el reporte de kilómetros y tiempo de uso del microservicio de reportes
        ReportDto report = reportFeignClient.getUsageReportByScooter(scooterId, true);

        // Verificar si cumple con los umbrales de mantenimiento
        boolean needsMaintenance = report.getKilometers() >= MAX_KM_TRAVELED ||
                report.getUsageTime() >= MAX_USAGE_TIME_MINUTES;

        if (needsMaintenance) {
            // Actualizar el estado del monopatín a "En Mantenimiento"
            Scooter scooter = scooterRepository.findById(scooterId)
                    .orElseThrow(() -> new NotFoundException("Scooter no encontrado"));
            scooter.setState(ScooterState.IN_MAINTENANCE);
            scooterRepository.save(scooter);
        }

        return needsMaintenance;
    }

    public List<ScooterDto> findScootersWithTrips(int year, int minTrips) {
        // Listado de id de los scooters que cumplen con los requisitos (year y cantidadViajes >= minTrips)
        List<Long> scooterIds = tripFeignClient.getScootersWithMinTrips(year, minTrips);

        // Obtenemos los scooters a partir de la lista de ids
        List<Scooter> scooters = scooterRepository.findAllById(scooterIds);

        return scooters.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public ScooterStatusCountDto getScooterStatusCounts() {
        int inUse = scooterRepository.countByState(ScooterState.IN_USE);
        int inMaintenance = scooterRepository.countByState(ScooterState.IN_MAINTENANCE);

        ScooterStatusCountDto statusCount = new ScooterStatusCountDto();
        statusCount.setInUse(inUse);
        statusCount.setInMaintenance(inMaintenance);

        return statusCount;
    }

    public List<ScooterDto> getScootersByLocation(double latitude, double longitude, double radio){
        List<Scooter> scooters = scooterRepository.getScootersByLocation(latitude, longitude, radio);
        return scooters.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    //***********************************************************************************************************
    
    private Scooter convertDtoToEntity(ScooterDto scooterDto) {
    	Scooter scooter = new Scooter();
    	scooter.setState(scooterDto.getState());
    	scooter.setKilometers(scooterDto.getKilometers());
    	scooter.setActiveTime(scooterDto.getActiveTime());
        scooter.setLatitude(scooterDto.getLatitude());
        scooter.setLongitude(scooterDto.getLongitude());
        return scooter;
    }

    private ScooterDto convertEntityToDto(Scooter scooter) {
    	ScooterDto scooterDto = new ScooterDto();
    	scooterDto.setState(scooter.getState());
    	scooterDto.setKilometers(scooter.getKilometers());
    	scooterDto.setActiveTime(scooter.getActiveTime());
        scooterDto.setLatitude(scooter.getLatitude());
        scooterDto.setLongitude(scooter.getLongitude());
        return scooterDto;
    }
}
