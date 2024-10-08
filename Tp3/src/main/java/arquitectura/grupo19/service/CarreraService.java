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
    
    public List<EstudianteCarreraDTO> getReporteDeCarrerasPorAnio(){
    	 List<EstudianteCarreraDTO> result = new ArrayList<>();
    	
    	 List<Carrera> carreras = carreraRepository.findAll(Sort.by("nombre").ascending());
    	 List<Integer> anios = this.getAnios();
    	 
    	 for (Carrera carrera : carreras) {
 			for (Integer anio : anios) {
 				Integer inscriptos = countInscriptosByCarreraAndAnio(carrera.getId(), anio);
 				Integer egresados = countEgresadosByCarreraAndAnio(carrera.getId(), anio);
 				if (inscriptos == null) inscriptos = 0;
 				if (egresados == null) egresados = 0;

 				if(inscriptos != 0 || egresados != 0)
 					result.add(new EstudianteCarreraDTO(carrera.getNombre(), anio, inscriptos, egresados));
 			}
 		}

		return result;
    }

	private List<Integer> getAnios() {
		List<Integer> aniosInscripcion = estudianteCarreraRepository.getAniosInscripcion();
		List<Integer> aniosGraduacion = estudianteCarreraRepository.getAniosGraduacion();
		
		// Combinar y eliminar duplicados
		Set<Integer> aniosUnicos = new HashSet<>();
		aniosUnicos.addAll(aniosInscripcion);
		aniosUnicos.addAll(aniosGraduacion);
		
		List<Integer> anios = new ArrayList<>(aniosUnicos);
		Collections.sort(anios);
		return anios;
	}
	//Métodos aux para generar reporte
		public Integer countInscriptosByCarreraAndAnio(int carrera, int anio) {
			return estudianteCarreraRepository.countInscriptosByCarreraAndAnio(carrera, anio);
		}

		public Integer countEgresadosByCarreraAndAnio(int carrera, int anio) {
			return estudianteCarreraRepository.countEgresadosByCarreraAndAnio(carrera, anio);
		}
}
