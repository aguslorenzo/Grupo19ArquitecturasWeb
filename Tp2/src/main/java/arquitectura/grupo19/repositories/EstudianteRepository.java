package arquitectura.grupo19.repositories;

import arquitectura.grupo19.db.Db;
import arquitectura.grupo19.entities.Estudiante;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

public class EstudianteRepository implements EstudianteServices {

	public Estudiante find(int id) {
		EntityManager em = Db.open();
		Estudiante e = em.find(Estudiante.class, id);
		Db.close();
		return e;
	}

	public void insert(Estudiante e){
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

	@Override
	public List<Estudiante> obtenerEstudiantesPorGenero(String genero) {
	    EntityManager em = Db.open();

	    List<Estudiante> listado = em.createQuery(
	            "SELECT e FROM Estudiante e WHERE e.genero = :genero", Estudiante.class)
	            .setParameter("genero", genero)
	            .getResultList();
	    
	    Db.close(); 
	    return listado;
	}
}
