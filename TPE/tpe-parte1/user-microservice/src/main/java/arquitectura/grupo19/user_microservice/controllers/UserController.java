package arquitectura.grupo19.user_microservice.controllers;

import arquitectura.grupo19.user_microservice.dto.UserDto;
import arquitectura.grupo19.user_microservice.exceptions.InsufficientFundsException;
import arquitectura.grupo19.user_microservice.exceptions.UserNotFoundException;
import arquitectura.grupo19.user_microservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/id/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody UserDto userDto) {
        userService.saveUser(userDto);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    public UserDto deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    /*****************************************************************/
    @GetMapping("/id/{id}/minbalance/{cost}")
    @ResponseStatus(HttpStatus.OK)
    public boolean hasSufficientBalance(@PathVariable long id, @PathVariable double cost) {
        return userService.hasSufficientBalance(id, cost); // calcula si tiene saldo > tarifaMinima (1 minuto)
    }

    @PostMapping("/id/{id}/deduct/{cost}")
    public ResponseEntity<?> deductBalance(@PathVariable long id, @PathVariable double cost) {
        try {
            userService.deductBalance(id, cost);
            return ResponseEntity.ok("Balance deducido con éxito");
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/id/{id}/notify/{message}")
    public void sendNotification(long id, String message){
        userService.sendNotification(id, message);
    }
}
