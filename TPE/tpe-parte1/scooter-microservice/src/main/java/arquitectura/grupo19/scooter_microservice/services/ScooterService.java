package arquitectura.grupo19.scooter_microservice.services;

import arquitectura.grupo19.scooter_microservice.dto.ScooterDto;
import arquitectura.grupo19.scooter_microservice.entities.Scooter;
import arquitectura.grupo19.scooter_microservice.entities.ScooterState;
import arquitectura.grupo19.scooter_microservice.exceptions.NotFoundException;
import arquitectura.grupo19.scooter_microservice.repositories.ScooterRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScooterService {

    private final ScooterRepository scooterRepository;
    private static final Duration MAX_USAGE_TIME_MINUTES = Duration.ofMinutes(43200); // 30 días
    private static final double MAX_KM_TRAVELED = 1000.0;

    public ScooterService(ScooterRepository scooterRepository) {
        this.scooterRepository = scooterRepository;
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
    public boolean checkIfScooterIsInAllowedLocation(Long scooterId, String location) {
        Scooter scooter = scooterRepository.findById(scooterId).orElseThrow(() -> new IllegalArgumentException("Scooter not found"));
        // TODO implementar la lógica para verificar si la ubicación es permitida.
        return scooter.getGpsLocation().equals(location);
    }

    // Comprobar si el monopatín está disponible
    public boolean isAvailable(Long scooterId) {
        Scooter scooter = scooterRepository.findById(scooterId)
                .orElseThrow(() -> new IllegalArgumentException("Scooter not found"));
        return scooter.getState() == ScooterState.AVAILABLE;
    }

    // Agregar tiempo de uso y si llegó al tiempo y kms max, poner en MANTENIMIENTO
    public void addTimeOfUse(long id, Duration time){
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Scooter not found"));
        Duration actualTime = scooter.getUsageTime().plus(time);
        if(actualTime.compareTo(MAX_USAGE_TIME_MINUTES) >= 0 && scooter.getKilometers() == MAX_KM_TRAVELED){
            scooter.setState(ScooterState.IN_MAINTENANCE);
        }
        scooter.setUsageTime(actualTime);
        scooterRepository.save(scooter);
    }

    // Obtener tiempo acumulado de uso
    public Duration getUsageTime(long id){
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

    //***********************************************************************************************************
    
    private Scooter convertDtoToEntity(ScooterDto scooterDto) {
    	Scooter scooter = new Scooter();
    	scooter.setState(scooterDto.getState());
    	scooter.setKilometers(scooterDto.getKilometers());
    	scooter.setActiveTime(scooterDto.getActiveTime());
        scooter.setGpsLocation(scooterDto.getGpsLocation());
        return scooter;
    }

    private ScooterDto convertEntityToDto(Scooter scooter) {
    	ScooterDto scooterDto = new ScooterDto();
    	scooterDto.setState(scooter.getState());
    	scooterDto.setKilometers(scooter.getKilometers());
    	scooterDto.setActiveTime(scooter.getActiveTime());
        return scooterDto;
    }
}
