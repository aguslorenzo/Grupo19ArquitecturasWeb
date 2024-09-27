package arquitectura.grupo19;

import java.util.List;

import arquitectura.grupo19.entities.Carrera;
import arquitectura.grupo19.entities.Estudiante;
import arquitectura.grupo19.entities.EstudianteCarrera;
import arquitectura.grupo19.entities.EstudianteCarreraId;
import arquitectura.grupo19.repositories.CarreraRepository;
import arquitectura.grupo19.repositories.EstudianteCarreraRepository;
import arquitectura.grupo19.repositories.EstudianteRepository;


public class Main { //TODO hacer diagrama de objetos
    public static void main(String[] args) {
    	
    	//INSERTANDO CARRERAS
        CarreraRepository cr = new CarreraRepository();
        cr.insert(new Carrera("Tudai", 2));
        cr.insert(new Carrera("Tuari", 2));
        cr.insert(new Carrera("Ingenieria en sistemas", 6));
        cr.insert(new Carrera("Licenciatura en fisica", 6));
        cr.delete(2);
        
        
      //A - INSERTANDO ESTUDIANTES - listo
        EstudianteRepository er = new EstudianteRepository();
        er.insert(new Estudiante("Manuel", "Perez", 22, "M", "41111111" , "La Plata" ));
        er.insert(new Estudiante("Manuela", "Perez", 22, "F", "41111112" , "La Plata" ));
        er.insert(new Estudiante("Jorge", "Perez", 22, "M", "41111113" , "La Plata" ));
        er.insert(new Estudiante("Veronica", "Perez", 22, "F", "41111114" , "La Plata" ));
        er.insert(new Estudiante("Romina", "Jolie", 22, "F", "41111115" , "Lujan" ));
        er.insert(new Estudiante("Gaston", "Rodriguez", 22, "M", "41111116" , "Balcarce" ));
        er.insert(new Estudiante("Pablo", "Disalvo", 22, "M", "41111117" , "Pergamino" ));
        er.insert(new Estudiante("Ramiro", "Lopez", 22, "M", "41111118" , "Azul" ));

        er.delete(3);

        //B - MATRICULANDO ESTUDIANTES - listo a nivel repository
        Estudiante e = er.find (1);
		Carrera c = cr.find(1);
        EstudianteCarreraRepository ecr = new EstudianteCarreraRepository();
        ecr.insert(new EstudianteCarrera(e, c, 2, false,0));
        ecr.insert(new EstudianteCarrera(er.find (2),cr.find(3),7,false,0));
        ecr.insert(new EstudianteCarrera(er.find (4),cr.find(1),2,false,0));
        ecr.insert(new EstudianteCarrera(er.find (5),cr.find(1),1,false,0));

        //C - RECUPERAR TODOS LOS ESTUDIANTES ORDENADOS POR APELLIDO ASCENDENTEMENTE
        List<Estudiante> estudiantes = er.obtenerEstudiantesOrdenadosApellido();
        System.out.println("------------------------C---------------------------");
        for (Estudiante estudiante : estudiantes){
            System.out.println(estudiante);
        }

        //D - RECUPERAR ESTUDIANTE POR LIBRETA (ID)
        System.out.println("------------------------D---------------------------");
        Estudiante e2 = er.find(4);
        System.out.println(e2);

        //E - LISTANDO ESTUDIANTES POR GENERO - listo a nivel repository, falta validar input
        List<Estudiante> estudiantesGeneroFemenino = er.obtenerEstudiantesPorGenero("F");
        System.out.println("------------------------E---------------------------");
        for (Estudiante estudiante : estudiantesGeneroFemenino) {
            System.out.println(estudiante);
        }

        //F - RECUPERAR LAS CARRERAS CON ESTUDIANTES INSCRIPTOS Y ORDENAR POR CANTIDAD DE INSCRIPTOS
        List<Carrera> carreras = ecr.getCarrerasConInscriptos();
        System.out.println("------------------------F---------------------------");
        for (Carrera c2 : carreras){
            System.out.println(c2);
        }

        //G- RECUPERAR LOS ESTUDIANTES DE UNA CARRERA FILTRADO POR CIUDAD
        List<Estudiante> estudiantes2 = er.obtenerEstudiantesPorCarreraFiltrados("Tudai","La Plata");
        System.out.println("------------------------G---------------------------");
        for (Estudiante e3 : estudiantes2) {
            System.out.println(e3);
        }

        //BUSQUEDA DE ESTUDIANTE CARRERA TODO revisar, esto que sería?
        EstudianteCarrera matricula = ecr.find(e, c);
        System.out.println("------------------------?---------------------------");
        System.out.println(matricula);
   
   
    }
}