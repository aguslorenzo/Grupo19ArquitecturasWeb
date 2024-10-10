package arquitectura.grupo19.dto;

import com.fasterxml.jackson.annotation.JsonView;

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
    private int anioGraduacion;
    
    //VISTA DE RESULTADOS PARA REPORTE////
    public static class VistaSimple {}
    @JsonView(VistaSimple.class)
    private int cantInscriptos;
    
    @JsonView(VistaSimple.class)
    private int cantEgresados;
    
    @JsonView(VistaSimple.class)
    private int anio;
    
    @JsonView(VistaSimple.class)
    private String nombreCarrera;
    /////////////////////////////////////

    public EstudianteCarreraDTO(int id, int idEstudiante, int idCarrera, int anioInscripcion, int anioGraduacion) {
        this.id = id;
        this.idEstudiante = idEstudiante;
        this.idCarrera = idCarrera;
        this.anioInscripcion = anioInscripcion;
        this.anioGraduacion = anioGraduacion;
    }
    
    public EstudianteCarreraDTO(String nombreCarrera, int anio, int cantInscriptos, int cantEgresados) {
        this.nombreCarrera = nombreCarrera;
        this.anio = anio;
        this.cantInscriptos = cantInscriptos;
        this.cantEgresados = cantEgresados;
    }

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

	public long getCantInscriptos() {
		return cantInscriptos;
	}

	public void setCantInscriptos(int cantInscriptos) {
		this.cantInscriptos = cantInscriptos;
	}

	public long getCantEgresados() {
		return cantEgresados;
	}

	public void setCantEgresados(int cantEgresados) {
		this.cantEgresados = cantEgresados;
	}


	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public String getNombreCarrera() {
		return nombreCarrera;
	}

	public void setNombreCarrera(String nombreCarrera) {
		this.nombreCarrera = nombreCarrera;
	}

	public int getAnioGraduacion() {
		return anioGraduacion;
	}

	public void setAnioGraduacion(int anioGraduacion) {
		this.anioGraduacion = anioGraduacion;
	}

	@Override
	public String toString() {
		return "EstudianteCarreraDTO [cantInscriptos=" + cantInscriptos + ", cantEgresados=" + cantEgresados + ", anio="
				+ anio + ", nombreCarrera=" + nombreCarrera + "]";
	}



	
}
