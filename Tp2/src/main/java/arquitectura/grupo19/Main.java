package arquitectura.grupo19;

import java.util.List;

import arquitectura.grupo19.dto.CarreraDto;
import arquitectura.grupo19.dto.EstudianteCarreraDto;
import arquitectura.grupo19.dto.EstudianteDto;
import arquitectura.grupo19.entities.Carrera;
import arquitectura.grupo19.repositories.CarreraRepository;
import arquitectura.grupo19.services.CarreraService;
import arquitectura.grupo19.services.EstudianteService;
import arquitectura.grupo19.utils.Genero;


public class Main {
    public static void main(String[] args) {
    	
    	//INSERTANDO CARRERAS
        CarreraRepository cr = new CarreraRepository();
        cr.insert(new Carrera("Tudai", 2));
        cr.insert(new Carrera("Tuari", 2));
        cr.insert(new Carrera("Ingenieria en sistemas", 6));
        cr.insert(new Carrera("Licenciatura en fisica", 6));
        cr.delete(2);

        EstudianteService estudianteService = new EstudianteService();
        CarreraService carreraService = new CarreraService();

        //A - INSERTANDO ESTUDIANTES - Listo
        estudianteService.insertarEstudiante(new EstudianteDto("Manuel", "Perez", 22, Genero.MASCULINO, 51125131, "La Plata"));
        estudianteService.insertarEstudiante(new EstudianteDto("Manuela", "Perez", 22, Genero.FEMENINO, 31719112 , "Mar del Plata"));
        estudianteService.insertarEstudiante(new EstudianteDto("Jorge", "Perez", 22, Genero.MASCULINO, 44111513 , "Tandil"));
        estudianteService.insertarEstudiante(new EstudianteDto("Veronica", "Perez", 22, Genero.FEMENINO, 46111114 , "Tandil"));
        estudianteService.insertarEstudiante(new EstudianteDto("Romina", "Jolie", 22, Genero.FEMENINO, 31311615 , "Lujan"));
        estudianteService.insertarEstudiante(new EstudianteDto("Gaston", "Rodriguez", 22, Genero.MASCULINO, 47157116 , "Balcarce"));
        estudianteService.insertarEstudiante(new EstudianteDto("Pablo", "Disalvo", 22, Genero.MASCULINO, 51614117 , "Pergamino"));
        estudianteService.insertarEstudiante(new EstudianteDto("Ramiro", "Lopez", 22, Genero.MASCULINO, 41111118 , "Azul"));

        //B - MATRICULANDO ESTUDIANTES - Listo
        estudianteService.inscribirEstudianteCarrera(1,1, 2010);
        estudianteService.inscribirEstudianteCarrera(2,3, 2015);
        estudianteService.inscribirEstudianteCarrera(4,1, 2015);
        estudianteService.inscribirEstudianteCarrera(5,1, 2009);
        estudianteService.inscribirEstudianteCarrera(5,3, 2020);
        
        //SERVICIO QUE ACTUALIZA DATOS SOBRE EL EGRESO DE UN ESTUDIANTE
        estudianteService.egresarEstudiante(1, 1, 2020);

        //C - RECUPERAR TODOS LOS ESTUDIANTES ORDENADOS POR APELLIDO ASCENDENTEMENTE - Listo
        System.out.println("------------------------C---------------------------");
        List<EstudianteDto> estudiantes = estudianteService.obtenerEstudiantesOrdenadosApellido();
        for (EstudianteDto e : estudiantes){
            System.out.println(e);
        }

        //D - RECUPERAR ESTUDIANTE POR LIBRETA (ID) - Listo
        System.out.println("------------------------D---------------------------");
        System.out.println(estudianteService.obtenerEstudiantePorLibreta(4));

        //E - LISTANDO ESTUDIANTES POR GENERO - Listo
        System.out.println("------------------------E---------------------------");
        List<EstudianteDto> estudiantes2 = estudianteService.obtenerEstudiantesPorGenero(Genero.FEMENINO);
        for (EstudianteDto e: estudiantes2){
            System.out.println(e);
        }

        //F - RECUPERAR LAS CARRERAS CON ESTUDIANTES INSCRIPTOS Y ORDENAR POR CANTIDAD DE INSCRIPTOS - Listo
        System.out.println("------------------------F---------------------------");
        List<CarreraDto> carreras = carreraService.getCarrerasConInscriptos();
        for (CarreraDto c : carreras){
            System.out.println(c);
        }

        //G- RECUPERAR LOS ESTUDIANTES DE UNA CARRERA FILTRADO POR CIUDAD - Listo:qq
        System.out.println("------------------------G---------------------------");
        List<EstudianteDto> estudiantes3 = estudianteService.obtenerEstudiantesPorCarreraFiltrados("Tudai","Tandil");
        if (estudiantes3!=null){
            for (EstudianteDto e : estudiantes3) {
                System.out.println(e);
            }
        }
        else{
            System.out.println("No hay estudiantes para esa combinacion de carrera y ciudad");
        }
        
        
       
        

        //3- REPORTE DE CARRERAS
        System.out.println("------------------------3---------------------------");
        List<EstudianteCarreraDto> reporteCarreras = carreraService.getCarrerasPorAnio();
        for (EstudianteCarreraDto c: reporteCarreras){
            System.out.println(c);
        }
    }
}