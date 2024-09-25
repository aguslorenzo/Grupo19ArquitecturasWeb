package arquitectura.grupo19;

import arquitectura.grupo19.entities.Carrera;
import arquitectura.grupo19.repositories.CarreraRepository;
import arquitectura.grupo19.repositories.EstudianteRepository;

public class Main {
    public static void main(String[] args) {
        CarreraRepository cr = new CarreraRepository();
        cr.insert(new Carrera("Prog3", 2));

    }
}