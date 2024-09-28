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
    
    public EstudianteCarrera() {
    }
    
	public EstudianteCarrera(Estudiante estudiante, Carrera carrera, int antiguedad, boolean graduado,
			int anioGraduacion) {
		super();
		this.estudiante = estudiante;
		this.carrera = carrera;
		this.antiguedad = antiguedad;
		this.graduado = graduado;
		this.anioGraduacion = anioGraduacion;
	}

	public EstudianteCarrera(Estudiante estudiante, Carrera carrera, int antiguedad, boolean graduado) {
		super();
		this.estudiante = estudiante;
		this.carrera = carrera;
		this.antiguedad = antiguedad;
		this.graduado = graduado;
	}

	public int getAntiguedad() {
		return antiguedad;
	}

	public void setAntiguedad(int antiguedad) {
		this.antiguedad = antiguedad;
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

	public Carrera getCarrera() {
		return carrera;
	}

	@Override
	public String toString() {
		return "Carrera=" + carrera.getNombre() + ", antiguedad=" + antiguedad
				+ ", graduado=" + graduado + ", anioGraduacion=" + anioGraduacion + "]";
	}
//	public String toString2() {
//		return "EstudianteCarrera [estudiante=" + estudiante + ", carrera=" + carrera + ", antiguedad=" + antiguedad
//				+ ", graduado=" + graduado + ", anioGraduacion=" + anioGraduacion + "]";
//	}
    
}
