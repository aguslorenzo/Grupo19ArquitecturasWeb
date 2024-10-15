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
    @NotNull(message = "El campo DNI es requerido")
    @Id
    private int dni;

    @NotBlank(message = "El campo nombre es requerido")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El campo apellido es requerido")
    @Column(nullable = false)
    private String apellido;

    @NotBlank(message = "El campo edad es requerido")
    @Column(nullable = false)
    private int edad;

    @NotBlank(message = "El campo genero es requerido")
    @Column(nullable = false)
    private String genero;

    @NotBlank(message = "El campo libreta es requerido")
    @Column(nullable = false)
    private int nroLibreta;

    @NotBlank(message = "El campo ciudad es requerido")
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
