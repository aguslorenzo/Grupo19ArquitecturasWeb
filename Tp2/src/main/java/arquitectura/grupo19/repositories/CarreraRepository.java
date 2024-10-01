package arquitectura.grupo19.repositories;

import arquitectura.grupo19.db.Db;
import arquitectura.grupo19.entities.Carrera;

import javax.persistence.EntityManager;

public class CarreraRepository implements Repository<Carrera, Integer>{

    @Override
    public Carrera find(Integer id) {
        EntityManager em = Db.open();
        Carrera c = em.find(Carrera.class, id);
        Db.close();
        return c;
    }

    @Override
    public void insert(Carrera c) {
        EntityManager em = Db.open();
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
        Db.close();
    }

    @Override
    public void update(Integer id, Carrera obj) {

    }

    @Override
	public void delete(Integer id) {
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
