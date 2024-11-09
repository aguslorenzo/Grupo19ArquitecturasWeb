package arquitectura.grupo19.user_microservice.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class PaymentAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double saldo;
    private String fechaAlta;

    @ManyToMany(mappedBy = "cuentas")
    private Set<User> users;

    public void descontarSaldo(){
        // TODO implementar
    }

    public void cargarSaldo(){
        // TODO implementar
    }

    public boolean tieneSaldo(){
        return saldo > 0;
    }

}
