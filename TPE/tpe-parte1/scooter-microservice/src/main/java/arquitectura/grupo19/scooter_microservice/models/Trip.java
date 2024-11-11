package arquitectura.grupo19.scooter_microservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String cellphone;
    private double balance; //saldo
}


