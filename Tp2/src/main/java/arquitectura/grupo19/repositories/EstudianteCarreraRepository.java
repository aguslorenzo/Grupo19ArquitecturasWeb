package arquitectura.grupo19.repositories;

import javax.persistence.EntityManager;

import arquitectura.grupo19.db.Db;
import arquitectura.grupo19.dto.EstudianteCarreraDto;
import arquitectura.grupo19.entities.Carrera;
import arquitectura.grupo19.entities.Estudiante;
import arquitectura.grupo19.entities.EstudianteCarrera;
import arquitectura.grupo19.entities.EstudianteCarreraId;

import java.util.ArrayList;
import java.util.List;

public class EstudianteCarreraRepository  {


	public EstudianteCarrera find(Estudiante e, Carrera c) {
		EstudianteCarreraId id = new EstudianteCarreraId(e.getNroLibreta(),c.getId());
		EntityManager em = Db.open();
		EstudianteCarrera ec = em.find(EstudianteCarrera.class, id);
		Db.close();
		return ec;
	}

	public List<EstudianteCarrera> getCarrerasEstudiante(int idEstudiante){
		EntityManager em = Db.open();
		List<EstudianteCarrera> result = em
				.createQuery("SELECT ec FROM EstudianteCarrera ec " +
						"WHERE ec.estudiante.id = :idEstudiante", EstudianteCarrera.class)
				.setParameter("idEstudiante",idEstudiante)
				.getResultList();
		Db.close();
		return result;
	}

	
	public void insert(EstudianteCarrera ec) {
		EntityManager em = Db.open();
		em.getTransaction().begin();
		em.merge(ec);
		em.getTransaction().commit();
		Db.close();

	}

	public void update(EstudianteCarrera ec) {
		EntityManager em = Db.open();
		em.getTransaction().begin();
		em.merge(ec);
		em.getTransaction().commit();
		Db.close();
	}
	
	
	public void delete(EstudianteCarreraId id) {
		// TODO Auto-generated method stub

	}

	public List<Carrera> getCarrerasConInscriptos(){
		EntityManager em = Db.open();
		List<Carrera> result = em
				.createQuery("SELECT c FROM EstudianteCarrera ec " +
						"JOIN ec.carrera c " +
						"GROUP BY c " +
						"HAVING COUNT(ec) > 0 " +
						"ORDER BY COUNT(ec) DESC", Carrera.class)
				.getResultList();
		Db.close();
		return result;
	}


	/*public List<EstudianteCarreraDto> getCarrerasPorAnio() {
		EntityManager em = Db.open();

		List<EstudianteCarreraDto> result = em.createQuery(
						"SELECT new arquitectura.grupo19.dto.EstudianteCarreraDto(ec.carrera, ec.anioInscripcion, " +
								"COUNT(ec), " +
								"SUM(CASE WHEN ec.graduado = true AND ec.anioGraduacion IS NOT NULL THEN 1 ELSE 0 END)) " +
								"FROM EstudianteCarrera ec " +
								"GROUP BY ec.carrera, ec.anioInscripcion " +
								"ORDER BY ec.carrera.nombre ASC, ec.anioInscripcion ASC", EstudianteCarreraDto.class)
				.getResultList();

		Db.close();
		return result;
	}*/

	public List<EstudianteCarreraDto> getCarrerasPorAnio() {
		EntityManager em = Db.open();
		List<Carrera> carreras = em.createQuery("SELECT c FROM Carrera c ORDER BY c.nombre ASC", Carrera.class)
				.getResultList();

		List<EstudianteCarreraDto> result = new ArrayList<>();

		for (Carrera carrera : carreras) {
			// Obtener los años de inscripción y graduación únicos
			List<Integer> aniosInscripcion = em.createQuery(
							"SELECT DISTINCT ec.anioInscripcion FROM EstudianteCarrera ec WHERE ec.carrera = :carrera", Integer.class)
					.setParameter("carrera", carrera)
					.getResultList();
			List<Integer> aniosGraduacion = em.createQuery(
							"SELECT DISTINCT ec.anioGraduacion FROM EstudianteCarrera ec WHERE ec.carrera = :carrera AND ec.anioGraduacion != 0", Integer.class)
					.setParameter("carrera", carrera)
					.getResultList();
			List<Integer> anios = new ArrayList<>();
			anios.addAll(aniosInscripcion);
			anios.addAll(aniosGraduacion);

			for (Integer anio : anios) {
				long cantInscriptos = countInscriptosByCarreraAndAnio(carrera, anio);
				long cantEgresados = countEgresadosByCarreraAndAnio(carrera, anio);

				EstudianteCarreraDto dto = new EstudianteCarreraDto(carrera, anio, cantInscriptos, cantEgresados);
				result.add(dto);
			}
		}

		Db.close();
		return result;
	}

	//TODO metodos aux para generar reporte
	public long countInscriptosByCarreraAndAnio(Carrera carrera, int anio) {
		EntityManager em = Db.open();
		return (long) em.createQuery(
						"SELECT COUNT(ec) FROM EstudianteCarrera ec " +
								"WHERE ec.carrera = :carrera AND ec.anioInscripcion = :anio")
				.setParameter("carrera", carrera)
				.setParameter("anio", anio)
				.getSingleResult();
	}

	public long countEgresadosByCarreraAndAnio(Carrera carrera, int anio) {
		EntityManager em = Db.open();
		return (long) em.createQuery(
						"SELECT COUNT(ec) FROM EstudianteCarrera ec " +
								"WHERE ec.carrera = :carrera AND ec.anioGraduacion = :anio AND ec.graduado = true")
				.setParameter("carrera", carrera)
				.setParameter("anio", anio)
				.getSingleResult();
	}

}
