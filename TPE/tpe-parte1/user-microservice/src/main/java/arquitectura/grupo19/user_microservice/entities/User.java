package arquitectura.grupo19.user_microservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor // Crea un constructor sin argumentos
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String fistName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String cellphone;

    private double saldo;
    /*@OneToOne
    private PaymentAccount paymentMethod;*/

    @ManyToMany
    @JoinTable(
            name = "user_payment_account",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_account_id")
    )
    private Set<PaymentAccount> paymentAccounts = new HashSet<>();

    public void activarMonopatin(){
        /*if(paymentMethod.tieneSaldo()){

        }*/
        // TODO implementar
    }

    public boolean cortarServicio(){
        // S
        return false;
    }

}
