package arquitectura.grupo19.service;

import arquitectura.grupo19.entity.Carrera;
import arquitectura.grupo19.repository.EstudianteCarreraRepository;

import java.util.List;

public class CarreraService {
    private EstudianteCarreraRepository estudianteCarreraRepository;
    public List<Carrera> getCarrerasConInscriptos(){
        return estudianteCarreraRepository.getCarrerasConInscriptos();
    }
}
