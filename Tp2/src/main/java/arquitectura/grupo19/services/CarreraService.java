package arquitectura.grupo19.services;

import arquitectura.grupo19.dto.CarreraDto;
import arquitectura.grupo19.entities.Carrera;
import arquitectura.grupo19.repositories.EstudianteCarreraRepository;

import java.util.ArrayList;
import java.util.List;

public class CarreraService {

    private EstudianteCarreraRepository estudianteCarreraRepository;

    public CarreraService(){
        estudianteCarreraRepository = new EstudianteCarreraRepository();
    }

    public List<CarreraDto> getCarrerasConInscriptos(){
        List<Carrera> carreras = estudianteCarreraRepository.getCarrerasConInscriptos();
        List<CarreraDto> resultado = new ArrayList<>();
        for (Carrera c: carreras){
            resultado.add(convertirCarrera(c));
        }
        return resultado;
    }

    private CarreraDto convertirCarrera(Carrera carrera){
        return new CarreraDto(carrera.getId(),carrera.getNombre(),carrera.getDuracion());
    }
}
