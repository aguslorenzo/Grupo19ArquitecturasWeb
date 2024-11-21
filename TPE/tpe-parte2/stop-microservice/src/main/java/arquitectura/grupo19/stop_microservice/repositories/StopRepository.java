package arquitectura.grupo19.stop_microservice.repositories;

import arquitectura.grupo19.stop_microservice.entities.Stop;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopRepository extends MongoRepository<Stop, String> {
}