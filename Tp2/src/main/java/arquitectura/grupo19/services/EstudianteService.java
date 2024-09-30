package arquitectura.grupo19.services;

import arquitectura.grupo19.dto.EstudianteDto;
import arquitectura.grupo19.entities.Carrera;
import arquitectura.grupo19.entities.Estudiante;
import arquitectura.grupo19.entities.EstudianteCarrera;
import arquitectura.grupo19.repositories.CarreraRepository;
import arquitectura.grupo19.repositories.EstudianteCarreraRepository;
import arquitectura.grupo19.repositories.EstudianteRepository;
import arquitectura.grupo19.utils.Genero;

import java.util.ArrayList;
import java.util.List;

public class EstudianteService {

    private EstudianteRepository estudianteRepository;
    private CarreraRepository carreraRepository;
    private EstudianteCarreraRepository estudianteCarreraRepository;

    public EstudianteService(){
        estudianteRepository = new EstudianteRepository();
        estudianteCarreraRepository = new EstudianteCarreraRepository();
        carreraRepository = new CarreraRepository();
    }

    public void insertarEstudiante(EstudianteDto estudiante){
        Estudiante e = new Estudiante(estudiante.getNombre(),estudiante.getApellido(),estudiante.getEdad(),estudiante.getGenero().getCodigo(),estudiante.getDni(),estudiante.getCiudad());
        estudianteRepository.insert(e);
    }

    public void inscribirEstudianteCarrera(int idEstudiante, int idCarrera, int anioInscripcion){
        Estudiante estudiante = estudianteRepository.find(idEstudiante);
        Carrera carrera = carreraRepository.find(idCarrera);
        estudianteCarreraRepository.insert(new EstudianteCarrera(estudiante,carrera,anioInscripcion,false));
    }

    public void egresarEstudiante(int idEstudiante, int idCarrera, int anio) {
        Estudiante e = estudianteRepository.find(idEstudiante);
        Carrera c = carreraRepository.find(idCarrera);
        EstudianteCarrera ec = estudianteCarreraRepository.find(e, c);

        ec.setGraduado(true);
        ec.setAnioGraduacion(anio);
        estudianteCarreraRepository.update(ec);
    }

    public List<EstudianteDto> obtenerEstudiantesOrdenadosApellido(){
        List<Estudiante> estudiantes = estudianteRepository.obtenerEstudiantesOrdenadosApellido();
        List<EstudianteDto> resultado = new ArrayList<>();
        for (Estudiante e : estudiantes){
            resultado.add(convertirEstudiante(e));
        }
        return resultado;
    }

    public EstudianteDto obtenerEstudiantePorLibreta(int idEstudiante){
        return convertirEstudiante(estudianteRepository.find(idEstudiante));
    }

    public List<EstudianteDto> obtenerEstudiantesPorGenero(Genero genero){
        List<Estudiante> estudiantes = estudianteRepository.obtenerEstudiantesPorGenero(genero.getCodigo());
        List<EstudianteDto> resultado = new ArrayList<>();
        for (Estudiante e: estudiantes){
            resultado.add(convertirEstudiante(e));
        }
        return resultado;
    }

    public List<EstudianteDto> obtenerEstudiantesPorCarreraFiltrados(String carrera, String ciudad){
        List<Estudiante> estudiantes = estudianteRepository.obtenerEstudiantesPorCarreraFiltrados(carrera,ciudad);
        List<EstudianteDto> resultado = new ArrayList<>();
        for (Estudiante e: estudiantes){
            resultado.add(convertirEstudiante(e));
        }
        return resultado.isEmpty()?null:resultado;
    }

    private EstudianteDto convertirEstudiante(Estudiante estudiante){
        List<EstudianteCarrera> infoCarreras = estudianteCarreraRepository.getCarrerasEstudiante(estudiante.getNroLibreta());
        return new EstudianteDto(estudiante.getNroLibreta(),estudiante.getNombre(),estudiante.getApellido(),estudiante.getEdad(), Genero.fromCodigo(estudiante.getGenero()),estudiante.getDni(),estudiante.getCiudad(),infoCarreras);
    }
}
