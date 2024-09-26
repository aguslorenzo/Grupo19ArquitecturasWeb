package arquitectura.grupo19.repositories;

import arquitectura.grupo19.db.Db;
import arquitectura.grupo19.entities.Estudiante;

import javax.persistence.EntityManager;
import java.sql.SQLException;

public class EstudianteRepository implements Repository<Estudiante> {

	@Override
	public Estudiante find(int id) {
		EntityManager em = Db.open();
		Estudiante e = em.find(Estudiante.class, id);
		Db.close();
		return e;
	}

	@Override
	public void insert(Estudiante e){
		EntityManager em = Db.open();
		em.persist(e);
		em.getTransaction().commit();
		Db.close();
	}

	@Override
	public void update(int id, Estudiante e) {

	}

	@Override
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
}
