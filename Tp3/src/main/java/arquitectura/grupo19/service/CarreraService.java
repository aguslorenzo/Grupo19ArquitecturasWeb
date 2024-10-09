package arquitectura.grupo19.service;

import arquitectura.grupo19.dto.CarreraDTO;
import arquitectura.grupo19.entity.Carrera;
import arquitectura.grupo19.exceptions.CarreraNotFoundException;
import arquitectura.grupo19.repository.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarreraService {
    @Autowired
    private CarreraRepository carreraRepository;
    public List<Carrera> getCarrerasConInscriptos(){
        List<CarreraDTO> carreraDTOs = carreraRepository.getCarrerasConInscriptos();
        if(carreraDTOs.isEmpty()){
            throw new CarreraNotFoundException("No se encontraron carreras con inscriptos.");
        }
        List<Carrera> carreras = carreraDTOs.stream()
                .map(dto -> {
                    Carrera carrera = new Carrera();
                    carrera.setId(dto.getId());
                    carrera.setNombre(dto.getNombre());
                    carrera.setDuracion(dto.getDuracion());
                    return carrera;
                })
                .collect(Collectors.toList());
        return carreras;
    }
}
