package arquitectura.grupo19.user_microservice.controllers;

import arquitectura.grupo19.user_microservice.dto.TripResponseDto;
import arquitectura.grupo19.user_microservice.dto.UserDto;
import arquitectura.grupo19.user_microservice.entities.User;
import arquitectura.grupo19.user_microservice.exceptions.InsufficientFundsException;
import arquitectura.grupo19.user_microservice.exceptions.UserNotFoundException;
import arquitectura.grupo19.user_microservice.models.ScooterLocation;
import arquitectura.grupo19.user_microservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
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
    @PostMapping("/{userId}/{paymentAccountId}")
    public ResponseEntity<?> addPaymentAccountToUser(@PathVariable Long userId, @PathVariable Long paymentAccountId) {
        User updatedUser = userService.addPaymentAccountToUser(userId, paymentAccountId);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}/minbalance/{cost}")
    @ResponseStatus(HttpStatus.OK)
    public boolean hasSufficientBalance(@PathVariable long id, @PathVariable double cost) {
        return userService.hasSufficientBalance(id, cost); // calcula si tiene saldo > tarifaMinima (1 minuto)
    }

    @PostMapping("/{id}/deduct/{cost}")
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

    @PostMapping("/{id}/notify/{message}")
    public void sendNotification(long id, String message){
        userService.sendNotification(id, message);
    }

    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<?> toggleAccountStatus(@PathVariable Long id) {
        try {
            userService.toggleAccountStatus(id);
            return ResponseEntity.ok("Estado de cuenta cambiado con éxito");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    /**
     * g) Como usuario quiero un listado de los monopatines cercanos a mi zona, para poder encontrar
     * un monopatín cerca de mi ubicación
     */
    @GetMapping("/nearby-scooters")
    public List<ScooterLocation> getNearbyScooters(@RequestParam double latitude,
                                                   @RequestParam double longitude,
                                                   @RequestParam double radius) {
        return userService.findNearbyScooters(latitude, longitude, radius);
    }

    /*****************************************************************/
    @PostMapping("/start-trip/user/{userId}/scooter/{scooterId}")
    public TripResponseDto startTrip(@PathVariable long userId, @PathVariable long scooterId) {
        return userService.startTrip(userId, scooterId);
    }
    @PutMapping("/stop-trip/trip/{tripId}/scooter/{scooterId}")
    public ResponseEntity<?> stopTrip(@PathVariable long tripId, @PathVariable long scooterId){
        return userService.stopTrip(tripId,scooterId);
    }
}
