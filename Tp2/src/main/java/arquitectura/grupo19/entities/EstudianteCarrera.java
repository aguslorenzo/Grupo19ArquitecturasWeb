package arquitectura.grupo19.entities;
import javax.persistence.*;
@Entity
@Table(name = "estudiante_carrera")
@IdClass(EstudianteCarreraId.class)
public class EstudianteCarrera {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_estudiante",referencedColumnName = "nroLibreta")
    private Estudiante estudiante;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_carrera",referencedColumnName = "id")
    private Carrera carrera;
    @Column(nullable = false)
    private int antiguedad;
    @Column(nullable = false)
    private boolean graduado;
    @Column
    private int anioGraduacion;
}
