package arquitectura.grupo19.map_microservice.services;

import arquitectura.grupo19.map_microservice.dto.ScooterDto;
import arquitectura.grupo19.map_microservice.entities.ScooterLocation;
import arquitectura.grupo19.map_microservice.feignClients.ScooterFeignClient;
import arquitectura.grupo19.map_microservice.repositories.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapService {

    @Autowired
    private MapRepository mapRepository;
    @Autowired
    private ScooterFeignClient scooterFeignClient;

    public List<ScooterDto> findScootersNearby(double latitude, double longitude, double radius) {
        return scooterFeignClient.getScootersByLocation(latitude, longitude, radius);
    }
}
