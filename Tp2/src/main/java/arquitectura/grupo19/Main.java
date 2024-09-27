package arquitectura.grupo19;

import java.util.List;

import arquitectura.grupo19.entities.Carrera;
import arquitectura.grupo19.entities.Estudiante;
import arquitectura.grupo19.entities.EstudianteCarrera;
import arquitectura.grupo19.entities.EstudianteCarreraId;
import arquitectura.grupo19.repositories.CarreraRepository;
import arquitectura.grupo19.repositories.EstudianteCarreraRepository;
import arquitectura.grupo19.repositories.EstudianteRepository;


public class Main {
    public static void main(String[] args) {
    	
    	//INSERTANDO CARRERAS
        CarreraRepository cr = new CarreraRepository();
        cr.insert(new Carrera("Prog3", 2));
        cr.insert(new Carrera("Turismo", 2));
        cr.delete(2);
        
        
      //INSERTANDO ESTUDIANTES
        EstudianteRepository er = new EstudianteRepository();
        er.insert(new Estudiante("Manuel", "Perez", 22, "M", "41111111" , "La Plata" ));
        er.insert(new Estudiante("Manuela", "Perez", 22, "F", "41111112" , "La Plata" ));
        er.insert(new Estudiante("Jorge", "Perez", 22, "M", "41111113" , "La Plata" ));
        er.insert(new Estudiante("Veronica", "Perez", 22, "F", "41111114" , "La Plata" ));
        er.delete(3);
        
        //LISTANDO ESTUDIANTES POR GENERO
        List<Estudiante> estudiantesGeneroFemenino = er.obtenerEstudiantesPorGenero("F");
        for (Estudiante estudiante : estudiantesGeneroFemenino) {
			System.out.println(estudiante);
		}

        //MATRICULANDO ESTUDIANTES
        Estudiante e = er.find (1);
		Carrera c = cr.find(1);
        EstudianteCarreraRepository ecr = new EstudianteCarreraRepository();
        ecr.insert(new EstudianteCarrera(e, c, 2, false,0));
        
        
        //BUSQUEDA DE ESTUDIANTE CARRERA
        EstudianteCarrera matricula = ecr.find(e, c);
        System.out.println(matricula);
   
   
    }
}