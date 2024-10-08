package arquitectura.grupo19.dto;

/**
 * Es importante que estén todos los métodos getter y setter, debido a que Jackson (biblioteca de
 * procesamiento de JSON) necesita estos métodos para poder serializar (convertir objetos Java a JSON)
 * y deserializar (convertir objetos JSON a java) los objetos correctamente.
 */
public class EstudianteCarreraDTO {
    private int id;
    private int idEstudiante;
    private int idCarrera;
    private int anioInscripcion;

    public int getId() {
        return id;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public int getAnioInscripcion() {
        return anioInscripcion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public void setAnioInscripcion(int anioInscripcion) {
        this.anioInscripcion = anioInscripcion;
    }
}
