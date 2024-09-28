package arquitectura.grupo19.dto;

import arquitectura.grupo19.entities.EstudianteCarrera;
import arquitectura.grupo19.utils.Genero;

import java.util.ArrayList;
import java.util.List;

public class EstudianteDto {
    private int nroLibreta;
    private String nombre;
    private String apellido;
    private int edad;
    private Genero genero;
    private int dni;
    private String ciudad;
    private List<EstudianteCarrera> carreras;

    public EstudianteDto(int nroLibreta, String nombre, String apellido, int edad, Genero genero, int dni, String ciudad, List<EstudianteCarrera> infoCarreras) {
        this.nroLibreta = nroLibreta;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.dni = dni;
        this.ciudad = ciudad;
        this.carreras = infoCarreras;
    }

    public EstudianteDto(String nombre, String apellido, int edad, Genero genero, int dni, String ciudad) {
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

    public String getApellido() {
        return apellido;
    }

    public int getEdad() {
        return edad;
    }

    public Genero getGenero() {
        return genero;
    }

    public int getDni() {
        return dni;
    }

    public String getCiudad() {
        return ciudad;
    }

    public List<EstudianteCarrera> getCarreras() {
        return new ArrayList<>(carreras);
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "nroLibreta=" + nroLibreta +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", genero='" + genero.getCodigo() + '\'' +
                ", dni=" + dni +
                ", ciudad='" + ciudad + '\'' +
                ", información carreras inscripto=" + carreras +
                '}';
    }
}
