package arquitectura.grupo19.dto;

import arquitectura.grupo19.entity.EstudianteCarrera;

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
    public EstudianteDTO(int nroLibreta, String nombre, String apellido, int edad, String genero, int dni, String ciudad, List<EstudianteCarrera> infoCarreras) {
        this.nroLibreta = nroLibreta;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.dni = dni;
        this.ciudad = ciudad;
        this.carreras = infoCarreras;
    }

    public EstudianteDTO(String nombre, String apellido, int edad, String genero, int dni, String ciudad) {
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

    public List<EstudianteCarrera> getCarreras() {
        return carreras;
    }

    public void setCarreras(List<EstudianteCarrera> carreras) {
        this.carreras = carreras;
    }
}
