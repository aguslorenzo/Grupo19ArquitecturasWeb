package arquitectura.grupo19.service;

import arquitectura.grupo19.dto.CarreraDTO;
import arquitectura.grupo19.entity.Carrera;
import arquitectura.grupo19.repository.CarreraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarreraService {

    @Autowired
    private CarreraRepository carreraRepository;

	public List<CarreraDTO> getCarrerasConInscriptos() {
		return carreraRepository.getCarrerasConInscriptos()
				.stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	private CarreraDTO convertToDTO(Carrera c){
			return new CarreraDTO(c.getId(),c.getNombre(),c.getDuracion());
		}
}
