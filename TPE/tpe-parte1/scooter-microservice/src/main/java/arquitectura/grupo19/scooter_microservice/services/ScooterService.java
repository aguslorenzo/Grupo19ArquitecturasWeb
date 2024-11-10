package arquitectura.grupo19.scooter_microservice.services;

import arquitectura.grupo19.scooter_microservice.dto.ScooterDto;
import arquitectura.grupo19.scooter_microservice.entities.Scooter;
import arquitectura.grupo19.scooter_microservice.repositories.ScooterRepository;
import arquitectura.grupo19.scooter_microservice.services.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScooterService {

    private final ScooterRepository scooterRepository;


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
        scooter.setStatus(scooterDto.getStatus());
        scooter.setKilometers(scooterDto.getKilometers());
        scooter.setActiveTime(scooterDto.getActiveTime());

        // Guardar los cambios
        scooterRepository.save(scooter);
    }

    public ScooterDto deleteScooter(Long id){
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Scooter", id));
        scooterRepository.delete(scooter);
        return convertEntityToDto(scooter);
    }
  
    
    private Scooter convertDtoToEntity(ScooterDto scooterDto) {
    	Scooter scooter = new Scooter();
    	scooter.setStatus(scooterDto.getStatus());
    	scooter.setKilometers(scooterDto.getKilometers());
    	scooter.setActiveTime(scooterDto.getActiveTime());
        return scooter;
    }

    private ScooterDto convertEntityToDto(Scooter scooter) {
    	ScooterDto scooterDto = new ScooterDto();
    	scooterDto.setStatus(scooter.getStatus());
    	scooterDto.setKilometers(scooter.getKilometers());
    	scooterDto.setActiveTime(scooter.getActiveTime());
        return scooterDto;
    }
}
