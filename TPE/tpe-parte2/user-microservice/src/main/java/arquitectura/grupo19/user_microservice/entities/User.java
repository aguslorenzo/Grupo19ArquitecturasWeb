package arquitectura.grupo19.user_microservice.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "'user'")
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
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String cellphone;
    @Column(nullable = false)
    private boolean isActive = true; // indica si la cuenta está activa o anulada

    @ManyToMany
    @JoinTable(
            name = "user_payment_account",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_account_id")
    )
    @JsonManagedReference
    private List<PaymentAccount> paymentAccounts = new ArrayList<>();

}
