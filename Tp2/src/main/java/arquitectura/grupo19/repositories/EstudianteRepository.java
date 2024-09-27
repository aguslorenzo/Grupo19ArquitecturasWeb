package arquitectura.grupo19.repositories;

import arquitectura.grupo19.db.Db;
import arquitectura.grupo19.entities.Carrera;
import arquitectura.grupo19.entities.Estudiante;
import javax.persistence.EntityManager;
import java.util.List;

public class EstudianteRepository implements Repository<Estudiante> {

	public Estudiante find(int id) {
		EntityManager em = Db.open();
		Estudiante e = em.find(Estudiante.class, id);
		Db.close();
		return e;
	}

	public void insert(Estudiante e) {
		EntityManager em = Db.open();
		em.persist(e);
		em.getTransaction().commit();
		Db.close();
	}

	public void update(int id, Estudiante e) {

	}

	public void delete(int id) {
		EntityManager em = Db.open();

		Estudiante e = em.find(Estudiante.class, id);
		if (e != null) {
			em.remove(e);
			em.getTransaction().commit();
		} else {
			System.out.println("El estudiante con id " + id + " no existe.");
		}

		Db.close();
	}

	public List<Estudiante> obtenerEstudiantesOrdenadosApellido(){//TODO generalizar si da el tiempo
		EntityManager em = Db.open();

		List<Estudiante> listado = em
				.createQuery("SELECT e FROM Estudiante e ORDER BY e.apellido ASC", Estudiante.class)
				.getResultList();

		Db.close();
		return listado;
	}

	public List<Estudiante> obtenerEstudiantesPorGenero(String genero) {
		EntityManager em = Db.open();

		List<Estudiante> listado = em
				.createQuery("SELECT e FROM Estudiante e WHERE e.genero = :genero", Estudiante.class)
				.setParameter("genero", genero).getResultList();

		Db.close();
		return listado;
	}

	public List<Estudiante> obtenerEstudiantesPorCarreraFiltrados(String carrera, String ciudad){
		EntityManager em = Db.open();
		List<Estudiante> resultado = em
				.createQuery("SELECT e FROM Estudiante e WHERE " +
						"e.ciudad = :ciudad AND " +
						"e.id IN (" +
						"SELECT ec.estudiante.id FROM EstudianteCarrera ec WHERE ec.carrera.id = (" +
						"SELECT c.id FROM Carrera c WHERE c.nombre = :carrera))", Estudiante.class)
				.setParameter("carrera", carrera)
				.setParameter("ciudad", ciudad)
				.getResultList();
		Db.close();
		return resultado;
	}



}
