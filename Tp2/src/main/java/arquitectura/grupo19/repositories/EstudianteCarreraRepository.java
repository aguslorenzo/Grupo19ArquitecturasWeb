package arquitectura.grupo19.repositories;

import javax.persistence.EntityManager;

import arquitectura.grupo19.db.Db;
import arquitectura.grupo19.entities.Carrera;
import arquitectura.grupo19.entities.Estudiante;
import arquitectura.grupo19.entities.EstudianteCarrera;
import arquitectura.grupo19.entities.EstudianteCarreraId;

public class EstudianteCarreraRepository  {


	public EstudianteCarrera find(Estudiante e, Carrera c) {
		EstudianteCarreraId id = new EstudianteCarreraId(e.getNroLibreta(),c.getId());
		EntityManager em = Db.open();
		EstudianteCarrera ec = em.find(EstudianteCarrera.class, id);
		Db.close();
		return ec;
	}

	
	public void insert(EstudianteCarrera ec) {
		Estudiante e = ec.getEstudiante();
		Carrera c = ec.getCarrera();
		
		EntityManager em = Db.open();
		em.merge(ec);
		em.getTransaction().commit();
		Db.close();
	}




	public void delete(EstudianteCarreraId id) {
		// TODO Auto-generated method stub

	}

}
