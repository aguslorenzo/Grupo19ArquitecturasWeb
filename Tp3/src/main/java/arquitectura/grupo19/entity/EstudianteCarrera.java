package arquitectura.grupo19.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Year;

@Entity
@Table(name = "estudiante_carrera")
@Getter
@Setter
@ToString

public class EstudianteCarrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estudiante", nullable = false)
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrera", nullable = false)
    private Carrera carrera;

    @Column(nullable = false)
    private int inscripcion;

    @Column(nullable = false)
    private int graduacion = 0;

    @Column(nullable = false)
    private int antiguedad;

    public EstudianteCarrera() {    	
    };
    
    public EstudianteCarrera(Estudiante estudiante, Carrera carrera, int inscripcion, int graduacion, int antiguedad) {
        this.estudiante = estudiante;
        this.carrera = carrera;
        //this.id = id;
        this.inscripcion = inscripcion;
        this.graduacion = graduacion;
        this.antiguedad = antiguedad;
    }

    /**
     * TODO Tentativa a usar cuando actualizamos el estado del alumno en la carrera, puede irse
     */
    public void calcularAntiguedad() {
        if (this.graduacion == 0) {
            this.antiguedad = Year.now().getValue() - this.inscripcion;
        } else {
            this.antiguedad = this.graduacion - this.inscripcion;
        }
    }
}
