package arquitectura.grupo19.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "estudiante")
@Getter
@Setter
@ToString

public class Estudiante {
    @Id
    private int dni;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(nullable = false)
    private int edad;
    @Column(nullable = false)
    private String genero;
    @Column(nullable = false)
    private int nroLibreta;
    @Column(nullable = false)
    private String ciudad;

    public Estudiante() {

    }

    public Estudiante(String nombre, String apellido, int edad, String genero, int dni, String ciudad, int nroLibreta) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.dni = dni;
        this.ciudad = ciudad;
        this.nroLibreta = nroLibreta;
    }
}
