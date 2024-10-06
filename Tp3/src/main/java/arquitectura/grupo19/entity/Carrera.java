package arquitectura.grupo19.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="carrera")
@Getter
@Setter
@ToString
@NoArgsConstructor

public class Carrera {
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private int duracion;
    
    public Carrera() {
    }
	public Carrera(String nombre, int duracion) {
		this.nombre = nombre;
		this.duracion = duracion;
	}
    
    
}
