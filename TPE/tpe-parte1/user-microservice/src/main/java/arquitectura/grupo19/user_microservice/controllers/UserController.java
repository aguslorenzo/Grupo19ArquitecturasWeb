package arquitectura.grupo19.user_microservice.controllers;

import arquitectura.grupo19.user_microservice.dto.UserDto;
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

    @PatchMapping("/user/{userId}/activarMonopatin/{scooterId}")
    public void activateScooter(@PathVariable long userId, @PathVariable long scooterId) {
        this.userService.activateScooter(userId, scooterId);
    }

    @GetMapping("/id/{id}/minbalance/{cost}")
    @ResponseStatus(HttpStatus.OK)
    public boolean hasSufficientBalance(@PathVariable long id, @PathVariable double cost) {
        return userService.hasSufficientBalance(id, cost); // calcula si tiene saldo > tarifaMinima (1 minuto)
    }

    @PatchMapping("/id/{id}/notify/{message}")
    public void notify(long id, String message){
        userService.notify(id, message);
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

}
