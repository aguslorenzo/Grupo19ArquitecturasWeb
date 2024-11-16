package arquitectura.grupo19.user_microservice.exceptions;

public class PaymentAccountNotFoundException extends  RuntimeException{

    public PaymentAccountNotFoundException(String message) { super(message);}
}
