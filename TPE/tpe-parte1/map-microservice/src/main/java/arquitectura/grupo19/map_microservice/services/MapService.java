package arquitectura.grupo19.map_microservice.services;

import arquitectura.grupo19.map_microservice.entities.ScooterLocation;
import arquitectura.grupo19.map_microservice.repositories.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapService {

    @Autowired
    private MapRepository mapRepository;

    public List<ScooterLocation> findScootersNearby(double latitude, double longitude, double radius) {
        return mapRepository.findScootersNearby(latitude, longitude, radius);
    }
}
