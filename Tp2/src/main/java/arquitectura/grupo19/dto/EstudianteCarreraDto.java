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

	@Override
	public String toString() {
		return "EstudianteCarrera {" +
				"carrera=" + carrera +
				", anio=" + anio +
				", cantInscriptos=" + cantInscriptos +
				", cantEgresados=" + cantEgresados +
				'}';
	}
}
