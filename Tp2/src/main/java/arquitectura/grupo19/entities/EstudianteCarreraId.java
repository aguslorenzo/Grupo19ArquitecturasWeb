package arquitectura.grupo19.entities;
import java.io.Serializable;
import java.util.Objects;
//TODO wtf
public class EstudianteCarreraId implements Serializable{
    private int idEstudiante;  // Debe coincidir con los tipos y nombres de los campos en la entidad Inscripcion
    private int idCarrera;

    // Constructor vacío (obligatorio para JPA)
    public EstudianteCarreraId() {}

    public EstudianteCarreraId(int idEstudiante, int idCarrera) {
        this.idEstudiante = idEstudiante;
        this.idCarrera = idCarrera;
    }

    // Getters, Setters, equals() y hashCode() deben estar implementados
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstudianteCarreraId that = (EstudianteCarreraId) o;
        return Objects.equals(idEstudiante, that.idEstudiante) &&
                Objects.equals(idCarrera, that.idCarrera);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEstudiante, idCarrera);
    }
}
