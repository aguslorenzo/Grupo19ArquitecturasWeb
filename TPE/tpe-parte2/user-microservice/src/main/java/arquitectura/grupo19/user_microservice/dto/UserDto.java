package arquitectura.grupo19.user_microservice.dto;

import arquitectura.grupo19.user_microservice.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull(message = "El alias es requerido")
    @NotEmpty( message = "El alias es un campo requerido")
    private String username;
    @NotNull(message = "El nombre es requerido")
    @NotEmpty( message = "El nombre es un campo requerido")
    private String firstName;
    @NotNull(message = "El apellido es requerido")
    @NotEmpty( message = "El apellido es un campo requerido")
    private String lastName;
    @NotNull(message = "El email es requerido")
    @NotEmpty( message = "El email es un campo requerido")
    @Email
    private String email;
    @NotNull(message = "El número de celular es requerido")
    @NotEmpty( message = "El número de celular es un campo requerido")
    private String cellphone;

    public UserDto(User user) {
        this.username = user.getUsername();
        this.firstName = user.getFistName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.cellphone = user.getCellphone();
    }

}
