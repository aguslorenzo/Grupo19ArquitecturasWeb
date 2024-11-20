package arquitectura.grupo19.api_gateway.controllers;

import arquitectura.grupo19.api_gateway.dto.RoleDto;
import arquitectura.grupo19.api_gateway.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("")
    public List<RoleDto> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public RoleDto getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @PostMapping("/add")
    public RoleDto addRole(@RequestBody RoleDto roleDto) {
        return roleService.addRole(roleDto);
    }

    @PutMapping("/update/{id}")
    public RoleDto updateRole(@PathVariable Long id, @RequestBody RoleDto roleDto) {
        return roleService.updateRole(id, roleDto);
    }

    @DeleteMapping("/delete/{id}")
    public RoleDto deleteRole(@PathVariable Long id) {
        return roleService.deleteRole(id);
    }
}
