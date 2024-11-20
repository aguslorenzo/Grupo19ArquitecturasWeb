package arquitectura.grupo19.api_gateway.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RoleDto {
    private String name;

    public RoleDto(String name) {
        this.name = name;
    }
}
