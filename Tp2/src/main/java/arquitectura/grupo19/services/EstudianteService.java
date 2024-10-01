package arquitectura.grupo19.services;

import arquitectura.grupo19.dto.EstudianteDto;
import arquitectura.grupo19.entities.*;
import arquitectura.grupo19.repositories.*;
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

    public void inscribirEstudianteCarrera(int idEstudiante, int idCarrera, int anioInscripcion) {
        Estudiante estudiante = estudianteRepository.find(idEstudiante);
        Carrera carrera = carreraRepository.find(idCarrera);

        // Verificamos que las entidades no sean nulas
        if (estudiante == null || carrera == null) {
            throw new IllegalArgumentException("Estudiante o Carrera no encontrados");
        }

        // Verificamos si el estudiante ya está inscrito en la carrera
        EstudianteCarreraId ecId = new EstudianteCarreraId(idEstudiante, idCarrera);
        EstudianteCarrera existsEc = estudianteCarreraRepository.find(ecId);
        if (existsEc != null) {
            throw new IllegalStateException("El estudiante ya está inscrito en la carrera.");
        }

        // Creamos la entidad EstudianteCarrera y la persistimos
        EstudianteCarrera ec = new EstudianteCarrera(estudiante, carrera, anioInscripcion, false);
        estudianteCarreraRepository.insert(ec);
    }


    public void egresarEstudiante(int idEstudiante, int idCarrera, int anio) {
        EstudianteCarreraId ecId = new EstudianteCarreraId(idEstudiante, idCarrera);
        EstudianteCarrera ec = estudianteCarreraRepository.find(ecId);

        if (ec != null) {
            // Si existe, actualizamos los datos del estudiante
            ec.setGraduado(true);
            ec.setAnioGraduacion(anio);
            estudianteCarreraRepository.update(ecId, ec);

        } else {
            // Si no existe, lanzamos una excepción
            System.out.println("No se encontró el estudiante con el ID o carrera especificados.");
        }
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

    // EXTRA: OBTENER ESTUDIANTES ORDENADOS POR CUALQUIER CAMPO
    public List<EstudianteDto> obtenerEstudiantesOrdenadosPor(String campoOrden, boolean ascendente){
        List<Estudiante> estudiantes = estudianteRepository.obtenerEstudiantesOrdenadosPor(campoOrden, ascendente);
        List<EstudianteDto> resultado = new ArrayList<>();
        for (Estudiante e : estudiantes){
            resultado.add(convertirEstudiante(e));
        }
        return resultado;
    }
}
