package arquitectura.grupo19.controller;


import arquitectura.grupo19.dto.EstudianteDTO;
import arquitectura.grupo19.entity.Estudiante;
import arquitectura.grupo19.exceptions.EstudianteNotFoundException;
import arquitectura.grupo19.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {
    @Autowired
    private EstudianteService estudianteService;

    @GetMapping
    public ResponseEntity<List<Estudiante>> obtenerEstudiantes() {
        List<Estudiante> estudiantes = estudianteService.obtenerEstudiantes();
        return ResponseEntity.ok(estudiantes);
    }

    @PostMapping("/guardar")
    public ResponseEntity<Estudiante> guardarEstudiante(@RequestBody Estudiante estudiante) {
        Estudiante nuevoEstudiante = estudianteService.guardarEstudiante(estudiante);
        return ResponseEntity.ok(nuevoEstudiante);
    }

    @GetMapping("/nrolibreta/{nroLibreta}")
    public ResponseEntity<?> obtenerEstudiante(@PathVariable int nroLibreta) {
        if (nroLibreta <= 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El número de libreta debe ser un entero positivo.");

        try {
            Estudiante estudiante = estudianteService.buscarEstudiantePorNroLibreta(nroLibreta);
            return ResponseEntity.ok(estudiante);
        } catch (EstudianteNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    
    @GetMapping("/obtenerPorCriterio/{criterio}/{asc}")
	public ResponseEntity<List<Estudiante>> obtenerEstudiantesOrdenadosPorCriterio(@PathVariable  String criterio, @PathVariable  boolean asc) {

		List<Estudiante> estudiantesOrdenados = new ArrayList<Estudiante>();

		try {
			estudiantesOrdenados.addAll(estudianteService.obtenerEstudiantesOrdenadosPorCriterio(criterio, asc));
		} catch (Exception e) {
			System.out.println("Error al buscar el listado" + e);
			return ResponseEntity.ok(estudiantesOrdenados);
		}
		
		if (estudiantesOrdenados.isEmpty()) {
	        System.out.println("No se encontraron estudiantes");
	    } else {
	        System.out.println("Listado ordenado según criterio exitoso");
	    }
		
		return ResponseEntity.ok(estudiantesOrdenados);
	}

    @GetMapping("/genero/{genero}")
    public ResponseEntity<?> obtenerEstudiantesPorGenero(@PathVariable String genero){
        if(genero==null || genero.trim().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El género no puede estar vacío.");
        }
        try {
            List<Estudiante> estudiantes = estudianteService.obtenerEstudiantesPorGenero(genero);
            return ResponseEntity.ok(estudiantes);
        }
        catch (EstudianteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado.");
        }
    }

    @GetMapping("/obtenerFiltrados/{carrera}/{ciudad}")
    public ResponseEntity<List<Estudiante>> obtenerEstudiantesPorCarreraFiltrados(@PathVariable String carrera,@PathVariable String ciudad){
    	
    	List<Estudiante> estudiantesFiltrados = new ArrayList<Estudiante>();
    	try {
    		estudiantesFiltrados.addAll(estudianteService.obtenerEstudiantesPorCarreraFiltrados(carrera, ciudad));
		} catch (Exception e) {
			System.out.println("Error al buscar el listado" + e);
			return ResponseEntity.ok(estudiantesFiltrados);
		}
		
		if (estudiantesFiltrados.isEmpty()) {
	        System.out.println("No se encontraron estudiantes");
	    } else {
	        System.out.println("Listado ordenado según criterio exitoso");
	    }
		return ResponseEntity.ok(estudiantesFiltrados);
    }
   
}
