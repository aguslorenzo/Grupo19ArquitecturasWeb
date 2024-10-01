package arquitectura.grupo19.entities;
import java.io.Serializable;
import java.util.Objects;

public class EstudianteCarreraId implements Serializable{
    private int estudiante;
    private int carrera;

    // Constructor vacío ya que es obligatorio para JPA
    public EstudianteCarreraId() {}

    public EstudianteCarreraId(int idEstudiante, int idCarrera) {
        this.estudiante = idEstudiante;
        this.carrera = idCarrera;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstudianteCarreraId that = (EstudianteCarreraId) o;
        return Objects.equals(estudiante, that.estudiante) &&
                Objects.equals(carrera, that.carrera);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estudiante, carrera);
    }
}
