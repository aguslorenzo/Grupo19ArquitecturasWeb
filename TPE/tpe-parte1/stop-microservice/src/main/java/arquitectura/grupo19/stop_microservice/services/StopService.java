package arquitectura.grupo19.stop_microservice.services;

import arquitectura.grupo19.stop_microservice.dto.StopDto;
import arquitectura.grupo19.stop_microservice.entities.Stop;
import arquitectura.grupo19.stop_microservice.feignClients.ScooterFeignClient;
import arquitectura.grupo19.stop_microservice.model.Scooter;
import arquitectura.grupo19.stop_microservice.repositories.StopRepository;
import arquitectura.grupo19.stop_microservice.services.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StopService {

    private final StopRepository stopRepository;
    private final ScooterFeignClient scooterFeignClient;

    public StopService(StopRepository stopRepository, ScooterFeignClient scooterFeignClient) {
        this.stopRepository = stopRepository;
        this.scooterFeignClient = scooterFeignClient;
    }

    @Transactional
    public StopDto saveStop(StopDto stopDto){
        // Convertir el DTO a entidad Stop
        Stop stop = convertDtoToEntity(stopDto);

        // Si pasa las validaciones, guardar parada en base de datos
        Stop save = stopRepository.save(stop);

        // Retornar DTO
        return convertEntityToDto(save);
    }

    @Transactional(readOnly = true) // Para que no guarde el estado y tengamos un mejor rendimiento de la consulta.
    public StopDto getStopById(Long id){
        return stopRepository.findById(id)
                .map(StopDto::new)
                .orElseThrow(()->new NotFoundException("Stop", id));
    }

    @Transactional(readOnly = true)
    public List<StopDto> getStops(){
        return stopRepository.findAll()
                .stream().map(StopDto::new).toList();
    }

    public void updateStop(Long id, StopDto stopDto){
        // Buscar parada por id
        Stop stop = stopRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Stop", id));

        // Actualizar los campos de parada con los datos nuevos
        stop.setDirectionDescription(stopDto.getDirectionDescription());
        stop.setXAxis(stopDto.getXAxis());
        stop.setYAxis(stopDto.getYAxis());
        stop.setScooterId(stopDto.getScooterId());

        // Guardar los cambios
        stopRepository.save(stop);
    }

    public StopDto deleteStop(Long id){
        Stop stop = stopRepository.findById(id)
                .orElseThrow(()->new NotFoundException("User", id));
        stopRepository.delete(stop);
        return convertEntityToDto(stop);
    }
    
    
    //SERVICIOS DE AGREGAR O QUITAR SCOOTER DE LA PARADA*******************************************************
    public void placeScooter(Long stopId, Long scooterId) {
        Stop stop = stopRepository.findById(stopId).orElseThrow(() -> new NotFoundException("Stop", stopId));
        
        // Verifica si la parada esta libre o no
        if (stop.getScooterId() != null) {
            throw new IllegalStateException("La parada ya tiene un scooter asignado.");
        }
        
        // Llama al FeignClient para obtener el Scooter
        Scooter scooter = scooterFeignClient.getScooterById(scooterId);

        // Asocia el scooterId a la parada
        stop.setScooterId(scooter.getId());

        // Guarda los cambios
        stopRepository.save(stop);
    }
    
    public void clearStop(Long stopId) {
        Stop stop = stopRepository.findById(stopId).orElseThrow(() -> new NotFoundException("Stop", stopId));
        
        //Vacia la parada
        stop.setScooterId(null);

        // Guarda los cambios
        stopRepository.save(stop);
    }
    //**************************************************************************************************************
    
    
    
    private Stop convertDtoToEntity(StopDto stopDto) {
    	Stop stop = new Stop();
    	stop.setDirectionDescription(stopDto.getDirectionDescription());
    	stop.setXAxis(stopDto.getXAxis());
    	stop.setYAxis(stopDto.getYAxis());
    	stop.setScooterId(stopDto.getScooterId());

        return stop;
    }

    private StopDto convertEntityToDto(Stop stop) {
    	StopDto stopDto = new StopDto();
    	stopDto.setDirectionDescription(stop.getDirectionDescription());
    	stopDto.setXAxis(stop.getXAxis());
    	stopDto.setYAxis(stop.getYAxis());
    	stopDto.setScooterId(stop.getScooterId());
        return stopDto;
    }
}
