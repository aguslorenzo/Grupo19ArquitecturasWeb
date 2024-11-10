package arquitectura.grupo19.trip_microservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String cellphone;
    private double balance; //saldo
    //private List<PaymentAccount> paymentAccounts;
}
