package arquitectura.grupo19.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nroLibreta;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(nullable = false)
    private int edad;
    @Column(nullable = false)
    private String genero;
    @Column(nullable = false)
    private int dni;
    @Column(nullable = false)
    private String ciudad;
    @OneToMany(mappedBy = "estudiante")
    private List<EstudianteCarrera> carreras;
    public Estudiante(){
    }
    public Estudiante(String nombre, String apellido, int edad, String genero, int dni, String ciudad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.dni = dni;
        this.ciudad = ciudad;
    }

    public int getNroLibreta() {
        return nroLibreta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "nroLibreta=" + nroLibreta +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", genero='" + genero + '\'' +
                ", dni=" + dni +
                ", ciudad='" + ciudad + '\'' +
                '}';
    }
}
