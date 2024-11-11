package arquitectura.grupo19.user_microservice.dto;

import arquitectura.grupo19.user_microservice.entities.PaymentAccount;
import arquitectura.grupo19.user_microservice.entities.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentAccountDto {

    @NotNull(message = "El saldo es requerido")
    @NotEmpty( message = "El saldo es un campo requerido")
    private double balance;
    @NotNull(message = "La fecha de alta es requerido")
    @NotEmpty( message = "La fecha de alta es un campo requerido")
    private LocalDate dischargeDate;

    public PaymentAccountDto(PaymentAccount paymentAccount) {
        this.balance = paymentAccount.getBalance();
        this.dischargeDate = paymentAccount.getDischargeDate();
    }
}
