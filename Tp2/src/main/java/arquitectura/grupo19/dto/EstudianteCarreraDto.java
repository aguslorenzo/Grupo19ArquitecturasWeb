package arquitectura.grupo19.dto;

import arquitectura.grupo19.entities.Carrera;

public class EstudianteCarreraDto {
    private Carrera carrera;
	private int anio;
	private long cantInscriptos;
	private long cantEgresados;

	public EstudianteCarreraDto(Carrera carrera, int anio, long cantInscriptos, long cantEgresados) {
	    this.carrera = carrera;
	    this.anio = anio;
	    this.cantInscriptos = cantInscriptos;
	    this.cantEgresados = cantEgresados;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public int getAnio() {
		return anio;
	}

	public long getCantInscriptos() {
		return cantInscriptos;
	}

	public long getCantEgresados() {
		return cantEgresados;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public void setCantInscriptos(long cantInscriptos) {
		this.cantInscriptos = cantInscriptos;
	}

	public void setCantEgresados(long cantEgresados) {
		this.cantEgresados = cantEgresados;
	}

	@Override
	public String toString() {
		return "EstudianteCarrera {" +
				"carrera=" + carrera.getNombre() +
				", anio=" + anio +
				", cantInscriptos=" + cantInscriptos +
				", cantEgresados=" + cantEgresados +
				'}';
	}
}