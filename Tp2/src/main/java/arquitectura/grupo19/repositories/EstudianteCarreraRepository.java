package arquitectura.grupo19.repositories;

import javax.persistence.EntityManager;

import arquitectura.grupo19.db.Db;
import arquitectura.grupo19.dto.EstudianteCarreraDto;
import arquitectura.grupo19.entities.Carrera;
import arquitectura.grupo19.entities.Estudiante;
import arquitectura.grupo19.entities.EstudianteCarrera;
import arquitectura.grupo19.entities.EstudianteCarreraId;

import java.util.List;

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
		return result;
	}

	
	public void insert(EstudianteCarrera ec) {
		EntityManager em = Db.open();
		em.persist(ec);
		em.getTransaction().commit();
		Db.close();
	}

	public void update(EstudianteCarrera ec) {
		EntityManager em = Db.open();
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
		return result;
	}

	public List<EstudianteCarreraDto> getCarrerasPorAnio() {
	    EntityManager em = Db.open();
	    List<EstudianteCarreraDto> result = em
	            .createQuery("SELECT new arquitectura.grupo19.dto.EstudianteCarreraDto(ec.carrera, MIN(ec.antiguedad)," +
	                    " COUNT(ec), " +
	                    " SUM(CASE WHEN ec.graduado = true THEN 1 ELSE 0 END)) " +
	                    " FROM EstudianteCarrera ec " +
	                    " GROUP BY ec.carrera " +
	                    " ORDER BY ec.carrera.nombre ASC", EstudianteCarreraDto.class)
	            .getResultList();
	    return result;
	}


}
