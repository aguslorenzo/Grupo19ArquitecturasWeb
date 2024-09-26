package arquitectura.grupo19.repositories;

import arquitectura.grupo19.db.Db;
import arquitectura.grupo19.entities.Carrera;
import arquitectura.grupo19.entities.Estudiante;

import javax.persistence.EntityManager;
import java.sql.SQLException;

public class CarreraRepository implements Repository<Carrera>{

    @Override
    public Carrera find(int id) {
        EntityManager em = Db.open();
        Carrera c = em.find(Carrera.class, id);
        Db.close();
        return c;
    }

    @Override
    public void insert(Carrera c) {
        EntityManager em = Db.open();
        em.persist(c);
        em.getTransaction().commit();
        Db.close();
    }

    @Override
    public void update(int id, Carrera obj) {

    }

    @Override
	public void delete(int id) {
		EntityManager em = Db.open();

		Carrera c = em.find(Carrera.class, id);
		if (c != null) {
			em.remove(c);
			em.getTransaction().commit();
		} else {
	        System.out.println("El estudiante con id " + id + " no existe.");
	    }
		

		Db.close();
	}
}
