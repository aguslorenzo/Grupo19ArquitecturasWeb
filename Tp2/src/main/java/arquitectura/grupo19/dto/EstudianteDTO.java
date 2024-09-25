package arquitectura.grupo19.dto;

import arquitectura.grupo19.entities.EstudianteCarrera;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDTO {
    private int nroLibreta;
    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private int dni;
    private String ciudad;
    private List<EstudianteCarrera> carreras;

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

    public String getGenero() {
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
}
