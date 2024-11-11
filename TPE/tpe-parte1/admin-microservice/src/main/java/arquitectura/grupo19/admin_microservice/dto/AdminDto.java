package arquitectura.grupo19.admin_microservice.dto;

import arquitectura.grupo19.admin_microservice.entities.Admin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {

    @NotNull(message = "El nombre es requerido")
    @NotEmpty(message = "El nombre es requerido")
    private String firstName;

    @NotNull(message = "El apellido es requerido")
    @NotEmpty(message = "El apellido es requerido")
    private String lastName;

    @NotNull(message = "El mail es requerido")
    @NotEmpty(message = "El mail es requerido")
    private String email;
    
    @NotNull(message = "El numero de celular es requerido")
    @NotEmpty(message = "El numero de celular es requerido")
    private String cellphone;

    public AdminDto(Admin admin) {
        this.firstName = admin.getFirstName();
        this.lastName = admin.getLastName();
        this.email = admin.getEmail();
        this.cellphone = admin.getCellphone();
    }
}
