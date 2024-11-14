package arquitectura.grupo19.map_microservice.services;

import arquitectura.grupo19.map_microservice.entities.ScooterLocation;
import arquitectura.grupo19.map_microservice.repositories.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MapService {

    @Autowired
    private MapRepository mapRepository;

    public void updateScooterLocation(Long scooterId, String gpsLocation) {
        ScooterLocation location = mapRepository.findById(scooterId)
                .orElse(new ScooterLocation());
        location.setScooterId(scooterId);
        location.setGpsLocation(gpsLocation);
        location.setLastUpdated(LocalDateTime.now());
        mapRepository.save(location);
    }

    public List<ScooterLocation> findScootersNearby(String location, double radius) {
        return mapRepository.findScootersNearby(location, radius);
    }
}
