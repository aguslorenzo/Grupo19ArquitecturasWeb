package arquitectura.grupo19.utils;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import arquitectura.grupo19.entity.Carrera;
import arquitectura.grupo19.entity.Estudiante;
import arquitectura.grupo19.entity.EstudianteCarrera;
import arquitectura.grupo19.repository.CarreraRepository;
import arquitectura.grupo19.repository.EstudianteCarreraRepository;
import arquitectura.grupo19.repository.EstudianteRepository;

@Component
public class DbHelper {

	@Autowired
	private EstudianteRepository estudianteRepository;
	@Autowired
	private CarreraRepository carreraRepository;
	@Autowired
	private EstudianteCarreraRepository estudianteCarreraRepository;

	public void populateDB() throws Exception {
	
		try {
			System.out.println("Populating DB...");
			processCSV("src/main/java/arquitectura/grupo19/csv/estudiantes.csv", "Estudiante");
			processCSV("src/main/java/arquitectura/grupo19/csv/carreras.csv", "Carrera");
			processCSV("src/main/java/arquitectura/grupo19/csv/estudianteCarrera.csv", "EstudianteCarrera");
			System.out.println("Datos insertados correctamente");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error al insertar datos en la base de datos: " + e.getMessage());
		}
	}

	private void processCSV(String filePath, String entity) {
		System.out.println("Insertando " + entity.toLowerCase() + "s...");

		try (CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(filePath))) {
			switch (entity) {
			case "Estudiante" -> processEstudiante(parser);
			case "Carrera" -> processCarrera(parser);
			case "EstudianteCarrera" -> processEstudianteCarrera(parser);
			default -> System.err.println("Datos inválidos en el archivo CSV: " + filePath);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	private void processEstudiante(CSVParser parser) {
		for (CSVRecord row : parser) {
			if (row.size() >= 7) {
				String dniString = row.get(0);
				String nombre = row.get(1);
				String apellido = row.get(2);
				String edadString = row.get(3);
				String genero = row.get(4);
				String ciudad = row.get(5);
				String libretaString = row.get(6);

				List<String> data = new ArrayList<>(
						Arrays.asList(dniString, nombre, apellido, edadString, genero, ciudad, libretaString));
				List<String> dataNumber = new ArrayList<>(Arrays.asList(dniString, edadString, libretaString));

				if (noDataEmpty(data)) {
					if (isValidNumber(dataNumber)) {
						try {
							int dni = Integer.parseInt(dniString);
							int edad = Integer.parseInt(edadString);
							int libreta = Integer.parseInt(libretaString);
							Estudiante estudiante = new Estudiante(nombre, apellido, edad, genero, dni, ciudad,
									libreta);
							estudianteRepository.save(estudiante);
						} catch (Exception e) {
							System.err.println("Error al persistir estudiante: " + e.getMessage());
						}
					}
				}
			}
		}
		System.out.println("Estudiantes insertados");
		
	}

	private void processCarrera(CSVParser parser) {
		for (CSVRecord row : parser) {
			if (row.size() >= 2) {
				String nombre = row.get(1);
				String duracionString = row.get(2);

				List<String> data = new ArrayList<>(Arrays.asList(nombre, duracionString));
				List<String> dataNumber = new ArrayList<>(Arrays.asList(duracionString));

				if (noDataEmpty(data)) {
					if (isValidNumber(dataNumber)) {
						try {
							int duracion = Integer.parseInt(duracionString);
							Carrera carrera = new Carrera(nombre, duracion);
							carreraRepository.save(carrera);
						} catch (Exception e) {
							System.err.println("Error al persistir carrera: " + e.getMessage());
						}
					}
				}
			}
		}
		System.out.println("Carreras insertadas");
	}

	private void processEstudianteCarrera(CSVParser parser) {
		
		for (CSVRecord row : parser) {
			if (row.size() >= 6) {

				String idEstudianteString = row.get(1);
				String idCarreraString = row.get(2);
				String inscripcionString = row.get(3);
				String graduacionString = row.get(4);
				String antiguedadString = row.get(5);

				List<String> data = new ArrayList<>(Arrays.asList(idEstudianteString, idCarreraString,
						inscripcionString, graduacionString, antiguedadString));
				List<String> dataNumber = new ArrayList<>(Arrays.asList(idEstudianteString, idCarreraString,
						inscripcionString, graduacionString, antiguedadString));
				
				if (noDataEmpty(data)) {
					if (isValidNumber(dataNumber)) {
						try {
							int idEstudiante = Integer.parseInt(idEstudianteString);
							int idCarrera = Integer.parseInt(idCarreraString);
							int inscripcion = Integer.parseInt(inscripcionString);
							int graduacion = Integer.parseInt(graduacionString);
							int antiguedad = Integer.parseInt(antiguedadString);

							//Obtengo Estudiante y Carrera para usarlos como parametros de la entidad EstudianteCarrera
							Optional<Estudiante> estudiante = estudianteRepository.findById(idEstudiante);
							Optional<Carrera> carrera = carreraRepository.findById(idCarrera);

							if (estudiante.isPresent() && carrera.isPresent()) {
								EstudianteCarrera estudianteCarrera = new EstudianteCarrera(estudiante.get(),
										carrera.get(), inscripcion, graduacion, antiguedad);
								estudianteCarreraRepository.save(estudianteCarrera);
							}
						} catch (Exception e) {
							System.err.println("Error al matricular estudiante: " + e.getMessage());
						}
					}
				}
			}
		}
		System.out.println("Alumnos matriculados");
	}

	//METODOS DE VALIDACION
	private boolean isValidNumber(List<String> dataNumber) {
		int contador = 0;
		for (int i = 0; i < dataNumber.size(); i++) {
			if (dataNumber.get(i) == null || dataNumber.get(i).isEmpty()) {
				return false;
			}
			try {
				Integer.parseInt(dataNumber.get(i));
				contador++;
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return contador == dataNumber.size();
	}

	private boolean noDataEmpty(List<String> data) {
		int contador = 0;
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i) == null || data.get(i).isEmpty()) {
				return false;
			}
			contador++;
		}
		return contador == data.size();
	}
}
