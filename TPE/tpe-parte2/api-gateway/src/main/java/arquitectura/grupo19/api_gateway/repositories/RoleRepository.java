package arquitectura.grupo19.api_gateway.repositories;

import arquitectura.grupo19.api_gateway.dto.RoleDto;
import arquitectura.grupo19.api_gateway.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    @Query("SELECT new arquitectura.grupo19.api_gateway.dto.RoleDto(r.name) FROM Role r")
    List<RoleDto> getAllRoles();
}
