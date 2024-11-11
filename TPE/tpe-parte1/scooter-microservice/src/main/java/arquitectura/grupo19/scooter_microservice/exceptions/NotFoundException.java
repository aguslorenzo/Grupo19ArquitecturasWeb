package arquitectura.grupo19.scooter_microservice.exceptions;

public class NotFoundException extends RuntimeException{

    private final String message;

    public NotFoundException(String entity, Long id) {
        this.message = String.format("La entidad %s con id %s no existe.", entity, id);
    }

}
