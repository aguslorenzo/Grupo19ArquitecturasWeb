package arquitectura.grupo19.api_gateway.services;

import arquitectura.grupo19.api_gateway.dto.RoleDto;
import arquitectura.grupo19.api_gateway.entities.Role;
import arquitectura.grupo19.api_gateway.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return roleRepository.getAllRoles();
    }

    @Override
    public RoleDto getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElse(null);

        if(role == null) {
            return null;
        }

        return new RoleDto(role.getName());
    }

    @Override
    public RoleDto addRole(RoleDto roleDto) {
        if(roleDto == null) {
            return null;
        }

        roleRepository.save(new Role(roleDto.getName()));

        return roleDto;
    }

    @Override
    public RoleDto updateRole(Long id, RoleDto roleDto) {
        Role role = roleRepository.findById(id).orElse(null);

        if(role == null || roleDto == null) {
            return null;
        }

        role.setName(roleDto.getName());

        roleRepository.save(role);

        return roleDto;
    }

    @Override
    public RoleDto deleteRole(Long id) {
        Role role = roleRepository.findById(id).orElse(null);
        if(role == null) {
            return null;
        }

        roleRepository.delete(role);

        return new RoleDto(role.getName());
    }

    /*public void createRolesDefault() {
        roleRepository.save(new Role(AuthorityConstant._ADMIN));
        roleRepository.save(new Role(AuthorityConstant._MAINTENANCE));
        roleRepository.save(new Role(AuthorityConstant._USER));
    }*/
}
