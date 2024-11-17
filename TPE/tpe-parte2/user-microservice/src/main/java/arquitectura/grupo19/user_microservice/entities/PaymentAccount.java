package arquitectura.grupo19.user_microservice.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
public class PaymentAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double balance; //saldo
    private LocalDate dischargeDate; //fecha de alta

    @JsonBackReference
    @ManyToMany(mappedBy = "paymentAccounts")
    private List<User> users;

    public PaymentAccount() {
        balance = 0;
        dischargeDate = LocalDate.now();
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

}
