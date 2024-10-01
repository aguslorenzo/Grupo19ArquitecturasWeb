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
    private int anioInscripcion;
    @Column(nullable = false)
    private boolean graduado;
    @Column
    private int anioGraduacion;
    
    public EstudianteCarrera() {
    }
    
	public EstudianteCarrera(Estudiante estudiante, Carrera carrera, int anioInscripcion, boolean graduado,
			int anioGraduacion) {
		super();
		this.estudiante = estudiante;
		this.carrera = carrera;
		this.anioInscripcion = anioInscripcion;
		this.graduado = graduado;
		this.anioGraduacion = anioGraduacion;
	}

	public EstudianteCarrera(Estudiante estudiante, Carrera carrera, int anioInscripcion, boolean graduado) {
		super();
		this.estudiante = estudiante;
		this.carrera = carrera;
		this.anioInscripcion = anioInscripcion;
		this.graduado = graduado;
	}

	public int getAnioInscripcion() {
		return anioInscripcion;
	}

	public void setAnioInscripcion(int anioInscripcion) {
		this.anioInscripcion = anioInscripcion;
	}

	public boolean isGraduado() {
		return graduado;
	}

	public void setGraduado(boolean graduado) {
		this.graduado = graduado;
	}

	public int getAnioGraduacion() {
		return anioGraduacion;
	}

	public void setAnioGraduacion(int anioGraduacion) {
		this.anioGraduacion = anioGraduacion;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante e){this.estudiante = e;}

	public void setCarrera(Carrera c){this.carrera = c;}

	public Carrera getCarrera() {
		return carrera;
	}

	@Override
	public String toString() {
		return "Carrera=" + carrera.getNombre() + ", anioInscripcion=" + anioInscripcion
				+ ", graduado=" + graduado + ", anioGraduacion=" + anioGraduacion + "]";
	}
    
}
