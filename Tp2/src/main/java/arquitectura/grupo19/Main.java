package arquitectura.grupo19;

import java.util.List;

import arquitectura.grupo19.entities.Carrera;
import arquitectura.grupo19.entities.Estudiante;
import arquitectura.grupo19.repositories.CarreraRepository;
import arquitectura.grupo19.repositories.EstudianteRepository;


public class Main {
    public static void main(String[] args) {
    	
        CarreraRepository cr = new CarreraRepository();
        cr.insert(new Carrera("Prog3", 2));
        cr.insert(new Carrera("Turismo", 2));
        cr.delete(1);
        
        EstudianteRepository er = new EstudianteRepository();
        er.insert(new Estudiante("Manuel", "Perez", 22, "M", "41111111" , "La Plata" ));
        er.insert(new Estudiante("Manuela", "Perez", 22, "F", "41111112" , "La Plata" ));
        er.insert(new Estudiante("Jorge", "Perez", 22, "M", "41111113" , "La Plata" ));
        er.insert(new Estudiante("Veronica", "Perez", 22, "F", "41111114" , "La Plata" ));
        er.delete(3);
        List<Estudiante> estudiantesGeneroFemenino = er.obtenerEstudiantesPorGenero("F");
        for (Estudiante estudiante : estudiantesGeneroFemenino) {
			System.out.println(estudiante);
		}
    }
}