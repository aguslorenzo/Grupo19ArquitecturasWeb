package arquitectura.grupo19.dto;

import arquitectura.grupo19.entity.EstudianteCarrera;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.List;

public class EstudianteDTO {
    @NotNull(message = "El campo libreta es requerido")
    private int nroLibreta;
    @NotBlank(message = "El campo nombre es requerido")
    private String nombre;
    @NotBlank(message = "El campo apellido es requerido")
    private String apellido;
    @NotNull(message = "El campo edad es requerido")
    private int edad;
    @NotBlank(message = "El campo genero es requerido")
    private String genero;
    @NotNull(message = "El campo DNI es requerido")
    private int dni;
    @NotBlank(message = "El campo ciudad es requerido")
    private String ciudad;

    public EstudianteDTO(int nroLibreta, String nombre, String apellido, int edad, String genero, int dni, String ciudad) {
        this.nroLibreta = nroLibreta;
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

    public void setNroLibreta(int nroLibreta) {
        this.nroLibreta = nroLibreta;
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
        return "EstudianteDTO{" +
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
