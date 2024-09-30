package arquitectura.grupo19.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Db {

    private static EntityManager em;
    private static EntityManagerFactory emf= Persistence.createEntityManagerFactory("Grupo19");

    private Db(){
    }

    public static EntityManager open() {
        if (em == null || !em.isOpen()) {
            em = emf.createEntityManager();
        }
        return em;
    }

    public static void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}
