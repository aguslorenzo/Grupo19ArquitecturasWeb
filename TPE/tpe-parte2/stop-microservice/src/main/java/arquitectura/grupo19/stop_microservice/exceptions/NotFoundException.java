package arquitectura.grupo19.stop_microservice.exceptions;

public class NotFoundException extends RuntimeException{

    private final String message;

    public NotFoundException(String entity, String id) {
        this.message = String.format("La entidad %s con id %s no existe.", entity, id);
    }

}
