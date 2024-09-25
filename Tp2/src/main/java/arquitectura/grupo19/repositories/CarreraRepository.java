package arquitectura.grupo19.repositories;

import arquitectura.grupo19.db.Db;
import arquitectura.grupo19.entities.Carrera;
import arquitectura.grupo19.entities.Estudiante;

import javax.persistence.EntityManager;
import java.sql.SQLException;

public class CarreraRepository implements Repository<Carrera>{

    @Override
    public Carrera find(long id) {
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
    public void update(long id, Carrera obj) {

    }

    @Override
    public void delete(long id) {

    }
}
