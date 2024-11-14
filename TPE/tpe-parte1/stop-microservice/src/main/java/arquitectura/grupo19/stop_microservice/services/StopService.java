package arquitectura.grupo19.stop_microservice.services;

import arquitectura.grupo19.stop_microservice.dto.StopDto;
import arquitectura.grupo19.stop_microservice.entities.Stop;
import arquitectura.grupo19.stop_microservice.exceptions.NotFoundException;
import arquitectura.grupo19.stop_microservice.repositories.StopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StopService {

    private final StopRepository stopRepository;

    public StopService(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
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
        stop.setLatitude(stopDto.getLatitude());
        stop.setLongitude(stopDto.getLongitude());
        // Guardar los cambios
        stopRepository.save(stop);
    }

    public StopDto deleteStop(Long id){
        Stop stop = stopRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Stop", id));
        stopRepository.delete(stop);
        return convertEntityToDto(stop);
    }


    /*****************************************************************/

    private Stop convertDtoToEntity(StopDto stopDto) {
    	Stop stop = new Stop();
    	stop.setDirectionDescription(stopDto.getDirectionDescription());
    	stop.setLatitude(stopDto.getLatitude());
        stop.setLongitude(stopDto.getLongitude());

        return stop;
    }

    private StopDto convertEntityToDto(Stop stop) {
    	StopDto stopDto = new StopDto();
    	stopDto.setDirectionDescription(stop.getDirectionDescription());
    	stopDto.setLatitude(stop.getLatitude());
        stopDto.setLongitude(stop.getLongitude());
        return stopDto;
    }
}
