package arquitectura.grupo19.dto;

import arquitectura.grupo19.entities.Carrera;
import arquitectura.grupo19.entities.Estudiante;

public class EstudianteCarreraDTO {
    private Estudiante estudiante;
    private Carrera carrera;
    private int antiguedad;
    private boolean graduado;
    private int anioGraduacion;
    
	public Estudiante getEstudiante() {
		return estudiante;
	}
	public Carrera getCarrera() {
		return carrera;
	}
	public int getAntiguedad() {
		return antiguedad;
	}
	public boolean isGraduado() {
		return graduado;
	}
	public int getAnioGraduacion() {
		return anioGraduacion;
	}
    
    
}
