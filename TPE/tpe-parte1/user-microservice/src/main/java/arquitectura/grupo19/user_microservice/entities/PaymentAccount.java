package arquitectura.grupo19.user_microservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
public class PaymentAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double balance;
    private LocalDate fechaAlta;

    @ManyToMany(mappedBy = "paymentAccounts")
    private List<User> users;

    public PaymentAccount() {
        balance = 0;
        fechaAlta = LocalDate.now(); // fecha actual
        users = new ArrayList<>();
    }

    public void deductBalance(double amount) {
        this.balance -= amount;
    }

    public void addBalance(double amount) {
        this.balance += amount;
    }

    public boolean hasSufficientBalance(double cost) {
        return balance >= cost;
    }
    /*public boolean hasBalance() {
        return balance > 0;
    }*/
}
