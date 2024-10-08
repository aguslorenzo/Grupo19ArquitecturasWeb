package arquitectura.grupo19.repository;

import arquitectura.grupo19.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante,Integer> {

    /**
     * Optional: obliga a manejar la posibilidad de que el valor esté presente o ausente.
     */
    Optional<Estudiante> findByNroLibreta(int nroLibreta);

}
