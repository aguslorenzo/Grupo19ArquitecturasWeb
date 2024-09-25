package arquitectura.grupo19.repositories;

import arquitectura.grupo19.db.Db;
import arquitectura.grupo19.entities.Estudiante;

import javax.persistence.EntityManager;
import java.sql.SQLException;

public class EstudianteRepository implements Repository<Estudiante>{

    @Override
    public Estudiante find(long id) {
        EntityManager em = Db.open();
        Estudiante e = em.find(Estudiante.class, id);
        Db.close();
        return e;
    }

    @Override
    public void insert(Estudiante e) throws SQLException {
        EntityManager em = Db.open();
        em.persist(e);
        em.getTransaction().commit();
        Db.close();
    }

    @Override
    public void update(long id, Estudiante e) {

    }

    @Override
    public void delete(long id) {

    }
}
