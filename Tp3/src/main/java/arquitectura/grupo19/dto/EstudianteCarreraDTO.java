package arquitectura.grupo19.dto;

import com.fasterxml.jackson.annotation.JsonView;

public class EstudianteCarreraDTO {

    public static class VistaBase {}
    @JsonView(VistaBase.class)
    private int id;
    @JsonView(VistaBase.class)
    private int idEstudiante;
    @JsonView(VistaBase.class)
    private int idCarrera;
    @JsonView(VistaBase.class)
    private int anioInscripcion;
    @JsonView(VistaBase.class)
    private int anioGraduacion;

    public static class VistaEstudianteMatriculado extends VistaBase {}
    @JsonView(VistaEstudianteMatriculado.class)
    private String nombreCarrera;
    
    //VISTA DE RESULTADOS PARA REPORTE////
    public static class VistaReporte {}
    @JsonView(VistaReporte.class)
    private int cantInscriptos;
    @JsonView(VistaReporte.class)
    private int cantEgresados;
    @JsonView(VistaReporte.class)
    private int anio;
    @JsonView(VistaReporte.class)
    private String carrera;

    /////////////////////////////////////

    public EstudianteCarreraDTO(int id, int idEstudiante, int idCarrera, int anioInscripcion, int anioGraduacion, String nombreCarrera) {
        this.id = id;
        this.idEstudiante = idEstudiante;
        this.idCarrera = idCarrera;
        this.anioInscripcion = anioInscripcion;
        this.anioGraduacion = anioGraduacion;
        this.nombreCarrera = nombreCarrera;
    }
    
    public EstudianteCarreraDTO(String nombreCarrera, int anio, int cantInscriptos, int cantEgresados) {
        this.carrera = nombreCarrera;
        this.anio = anio;
        this.cantInscriptos = cantInscriptos;
        this.cantEgresados = cantEgresados;
    }

	@Override
	public String toString() {
		return "EstudianteCarrera [cantInscriptos=" + cantInscriptos + ", cantEgresados=" + cantEgresados + ", anio="
				+ anio + ", nombreCarrera=" + nombreCarrera + "]";
	}
}
