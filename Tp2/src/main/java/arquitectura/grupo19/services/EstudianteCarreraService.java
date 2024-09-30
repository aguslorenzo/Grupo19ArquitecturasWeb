package arquitectura.grupo19.services;

import arquitectura.grupo19.entities.Carrera;
import arquitectura.grupo19.entities.Estudiante;
import arquitectura.grupo19.entities.EstudianteCarrera;
import arquitectura.grupo19.repositories.CarreraRepository;
import arquitectura.grupo19.repositories.EstudianteCarreraRepository;
import arquitectura.grupo19.repositories.EstudianteRepository;

public class EstudianteCarreraService {

	private EstudianteCarreraRepository estudianteCarreraRepository;
	private EstudianteRepository estudianteRepository;
	private CarreraRepository carreraRepository;
	
	public EstudianteCarreraService(){
		carreraRepository = new CarreraRepository();
		estudianteRepository = new EstudianteRepository();
        estudianteCarreraRepository = new EstudianteCarreraRepository();
    }
	
	public void egresarEstudiante(int idEstudiante, int idCarrera, int anio) {
		Estudiante e = estudianteRepository.find(idEstudiante);
		Carrera c = carreraRepository.find(idCarrera);
		EstudianteCarrera ec = estudianteCarreraRepository.find(e, c);
		
		ec.setGraduado(true);
		ec.setAnioGraduacion(anio);
		estudianteCarreraRepository.update(ec);
	}
	
}
