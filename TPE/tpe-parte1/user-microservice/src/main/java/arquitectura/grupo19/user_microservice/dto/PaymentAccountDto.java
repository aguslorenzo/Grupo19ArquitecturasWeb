package arquitectura.grupo19.user_microservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class PaymentAccountDto {

    @NotNull(message = "El saldo es requerido")
    @NotEmpty( message = "El alias es un campo requerido")
    private String saldo;
    @NotNull(message = "El nombre es requerido")
    @NotEmpty( message = "El nombre es un campo requerido")
    private String fechaAlta;


}
