package arquitectura.grupo19.api_gateway.services;

import arquitectura.grupo19.api_gateway.dto.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> getAllRoles();
    RoleDto getRoleById(Long id);
    RoleDto addRole(RoleDto roleDto);
    RoleDto updateRole(Long id, RoleDto roleDto);
    RoleDto deleteRole(Long id);
}
