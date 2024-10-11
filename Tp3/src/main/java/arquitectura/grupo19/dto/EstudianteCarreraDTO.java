package arquitectura.grupo19.dto;

import com.fasterxml.jackson.annotation.JsonView;

/*
 * TODO Es importante que estén todos los métodos getter y setter, debido a que Jackson (biblioteca de
 * procesamiento de JSON) necesita estos métodos para poder serializar (convertir objetos Java a JSON)
 * y deserializar (convertir objetos JSON a java) los objetos correctamente.
 */
public class EstudianteCarreraDTO {

    //TODO charlar grupalmente si dejamos esta logica de dos views en un archivo o separamos en 2 dto
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
    
    //VISTA DE RESULTADOS PARA REPORTE////
    public static class VistaReporte {}
    @JsonView(VistaReporte.class)
    private int cantInscriptos;
    
    @JsonView(VistaReporte.class)
    private int cantEgresados;
    
    @JsonView(VistaReporte.class)
    private int anio;
    
    @JsonView(VistaReporte.class)
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
