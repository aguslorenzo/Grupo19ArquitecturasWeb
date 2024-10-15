package arquitectura.grupo19.service;

import arquitectura.grupo19.dto.CarreraDTO;
import arquitectura.grupo19.dto.EstudianteCarreraDTO;
import arquitectura.grupo19.entity.Carrera;
import arquitectura.grupo19.exceptions.CarreraNotFoundException;
import arquitectura.grupo19.repository.CarreraRepository;
import arquitectura.grupo19.repository.EstudianteCarreraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CarreraService {
    @Autowired
    private CarreraRepository carreraRepository;
    @Autowired 
    private EstudianteCarreraRepository estudianteCarreraRepository;

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
