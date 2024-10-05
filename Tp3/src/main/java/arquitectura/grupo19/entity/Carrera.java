package arquitectura.grupo19.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="carrera")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private int duracion;
}
