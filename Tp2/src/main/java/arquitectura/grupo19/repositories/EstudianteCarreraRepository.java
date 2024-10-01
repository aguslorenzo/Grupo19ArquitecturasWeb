package arquitectura.grupo19.repositories;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import arquitectura.grupo19.db.Db;
import arquitectura.grupo19.dto.EstudianteCarreraDto;
import arquitectura.grupo19.entities.Carrera;
import arquitectura.grupo19.entities.Estudiante;
import arquitectura.grupo19.entities.EstudianteCarrera;
import arquitectura.grupo19.entities.EstudianteCarreraId;

import java.util.*;

public class EstudianteCarreraRepository implements Repository<EstudianteCarrera, EstudianteCarreraId>{


	@Override
	public EstudianteCarrera find(EstudianteCarreraId ecId) {
		EntityManager em = Db.open();
		EstudianteCarrera ec = em.find(EstudianteCarrera.class, ecId);
		Db.close();
		return ec;
	}

	@Override
	public void insert(EstudianteCarrera ec) {
		EntityManager em = Db.open();

		try {
			em.getTransaction().begin();

			Estudiante estudiante = em.find(Estudiante.class, ec.getEstudiante().getNroLibreta());
			Carrera carrera = em.find(Carrera.class, ec.getCarrera().getId());

			// Asignamos las entidades gestionadas a la nueva entidad
			ec.setEstudiante(estudiante);
			ec.setCarrera(carrera);

			// Persistimos la nueva entidad
			em.persist(ec);

			em.getTransaction().commit();

		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();

		} finally {
			Db.close();
		}
	}


	@Override
	public void update(EstudianteCarreraId id, EstudianteCarrera ec) {
		EntityManager em = Db.open();

		try {
			em.getTransaction().begin();
			em.merge(ec);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace(); // Manejo de excepciones
		} finally {
			Db.close();
		}
	}

	@Override
	public void delete(EstudianteCarreraId id) {
		// TODO Auto-generated method stub
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

	public List<EstudianteCarreraDto> getCarrerasPorAnio() {
		EntityManager em = Db.open();

		List<Carrera> carreras = em.createQuery("SELECT c FROM Carrera c ORDER BY c.nombre ASC", Carrera.class)
				.getResultList();

		List<EstudianteCarreraDto> result = new ArrayList<>();

		// Obtener los años de inscripción y graduación únicos
		List<Integer> aniosInscripcion = em.createQuery(
						"SELECT DISTINCT ec.anioInscripcion FROM EstudianteCarrera ec WHERE ec.anioInscripcion IS NOT NULL", Integer.class)
				.getResultList();
		List<Integer> aniosGraduacion = em.createQuery(
						"SELECT DISTINCT ec.anioGraduacion FROM EstudianteCarrera ec WHERE ec.anioGraduacion IS NOT NULL AND ec.anioGraduacion > 0", Integer.class)
				.getResultList();

		// Combinar y eliminar duplicados
		Set<Integer> aniosUnicos = new HashSet<>();
		aniosUnicos.addAll(aniosInscripcion);
		aniosUnicos.addAll(aniosGraduacion);

		// Convertir el conjunto a lista y ordenarla
		List<Integer> anios = new ArrayList<>(aniosUnicos);
		Collections.sort(anios);

		for (Carrera carrera : carreras) {
			for (Integer anio : anios) {
				long cantInscriptos = countInscriptosByCarreraAndAnio(carrera, anio);
				long cantEgresados = countEgresadosByCarreraAndAnio(carrera, anio);

				if(cantInscriptos != 0 || cantEgresados != 0){
					EstudianteCarreraDto dto = new EstudianteCarreraDto(carrera, anio, cantInscriptos, cantEgresados);
					result.add(dto);
				}
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
