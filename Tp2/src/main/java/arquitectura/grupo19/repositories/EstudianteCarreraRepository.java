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

		// Cargar las entidades Estudiante y Carrera en la sesión actual
		Estudiante estudiante = em.find(Estudiante.class, ec.getEstudiante().getNroLibreta());
		Carrera carrera = em.find(Carrera.class, ec.getCarrera().getId());

		// Verificar que las entidades no sean nulas
		if (estudiante == null || carrera == null) {
			throw new IllegalArgumentException("Estudiante o Carrera no encontrados");
		}

		// Verificar si la entidad EstudianteCarrera ya existe
		EstudianteCarreraId id = new EstudianteCarreraId(estudiante.getNroLibreta(), carrera.getId());
		EstudianteCarrera existsEc = em.find(EstudianteCarrera.class, id);

		if (existsEc == null) {
			// Si no existe, asignar las entidades gestionadas a la nueva entidad
			ec.setEstudiante(estudiante);
			ec.setCarrera(carrera);

			// Persistir la nueva entidad
			em.persist(ec);
		} else {
			// Si existe, manejar el caso de duplicación
			throw new IllegalStateException("El estudiante ya está inscrito en la carrera.");
		}

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


	/*public List<EstudianteCarreraDto> getCarrerasPorAnio(){
		EntityManager em = Db.open();
		List<EstudianteCarreraDto> result = new ArrayList<>();

		String jpql = "SELECT ec.carrera.nombre, ec.anioInscripcion, " +
				"SUM(CASE WHEN ec.graduado = false THEN 1 ELSE 0 END) AS inscriptos, " +
				"SUM(CASE WHEN ec.graduado = true AND ec.anioGraduacion IS NOT NULL THEN 1 ELSE 0 END) AS egresados " +
				"FROM EstudianteCarrera ec " +
				"GROUP BY ec.carrera.nombre, ec.anioInscripcion " +
				"ORDER BY ec.carrera.nombre ASC, ec.anioInscripcion ASC";
		TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
		List<Object[]> results = query.getResultList();

		for (Object[] row : results) {
			Carrera carrera = (Carrera) row[0];
			Integer anio = (Integer) row[1];
			int inscriptos = (int) row[2];
			int egresados = (int) row[3];

			EstudianteCarreraDto dto = new EstudianteCarreraDto(carrera, anio, inscriptos, egresados);
				result.add(dto);

			System.out.println("Carrera: " + carrera + ", Año: " + anio +
					", Inscriptos: " + inscriptos + ", Egresados: " + egresados);
		}

		Db.close();
		return result;
	}*/

}
