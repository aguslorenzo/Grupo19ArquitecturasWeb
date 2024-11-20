package arquitectura.grupo19.user_microservice.services;

import arquitectura.grupo19.user_microservice.dto.UserDto;
import arquitectura.grupo19.user_microservice.dto.TripResponseDto;
import arquitectura.grupo19.user_microservice.entities.PaymentAccount;
import arquitectura.grupo19.user_microservice.entities.User;
import arquitectura.grupo19.user_microservice.exceptions.InsufficientFundsException;
import arquitectura.grupo19.user_microservice.exceptions.PaymentAccountNotFoundException;
import arquitectura.grupo19.user_microservice.exceptions.UserNotFoundException;
import arquitectura.grupo19.user_microservice.feignClients.MapFeignClient;
import arquitectura.grupo19.user_microservice.feignClients.ScooterFeignClient;
import arquitectura.grupo19.user_microservice.feignClients.TripFeignClient;
import arquitectura.grupo19.user_microservice.models.ScooterLocation;
import arquitectura.grupo19.user_microservice.repositories.PaymentAccountRepository;
import arquitectura.grupo19.user_microservice.repositories.UserRepository;
import arquitectura.grupo19.user_microservice.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PaymentAccountRepository paymentAccountRepository;
    private final MapFeignClient mapFeignClient;
    private final ScooterFeignClient scooterFeignClient;
    private final TripFeignClient tripFeignClient;

    public UserService(UserRepository userRepository, PaymentAccountRepository paymentAccountRepository, MapFeignClient mapFeignClient, ScooterFeignClient scooterFeignClient, TripFeignClient tripFeignClient) {
        this.userRepository = userRepository;
        this.paymentAccountRepository = paymentAccountRepository;
        this.mapFeignClient = mapFeignClient;
        this.scooterFeignClient = scooterFeignClient;
        this.tripFeignClient = tripFeignClient;
    }

    @Transactional(readOnly = true)
    public List<UserDto> getUsers(){
        return userRepository.findAll()
                .stream().map(UserDto::new).toList();
    }

    @Transactional(readOnly = true) // Para que no guarde el estado y tengamos un mejor rendimiento de la consulta.
    public UserDto getUserById(Long id){
        return userRepository.findById(id)
                .map(UserDto::new)
                .orElseThrow(()->new NotFoundException("User", id));
    }

    @Transactional
    public UserDto saveUser(UserDto userDto){
        User user = convertDtoToEntity(userDto);
        User save = userRepository.save(user);
        return convertEntityToDto(save);
    }

    public void updateUser(Long id, UserDto userDto){
        User user = userRepository.findById(id)
                .orElseThrow(()->new NotFoundException("User", id));

        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setCellphone(userDto.getCellphone());

        userRepository.save(user);
    }

    public UserDto deleteUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()->new NotFoundException("User", id));
        userRepository.delete(user);
        return convertEntityToDto(user);
    }

    /*****************************************************************/

    @Transactional
    public User addPaymentAccountToUser(Long userId, Long paymentAccountId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        PaymentAccount pA = paymentAccountRepository.findById(paymentAccountId)
                .orElseThrow(() -> new PaymentAccountNotFoundException("Payment account with id " + paymentAccountId + " not found"));

        user.getPaymentAccounts().add(pA);
        return userRepository.save(user);
    }

    @Transactional
    public boolean hasSufficientBalance(long id, double cost){
        List<PaymentAccount> paymentAccounts = userRepository.findAccountsWithSufficientBalance(id, cost);
        return !paymentAccounts.isEmpty();
    }

    @Transactional
    public void deductBalance(long id, double cost) {
        // Recuperar todas las cuentas con saldo suficiente
        List<PaymentAccount> paymentAccounts = userRepository.findAccountsWithSufficientBalance(id, cost);

        // Verificar y deducir saldo en la primera cuenta encontrada, si existe
        PaymentAccount account = paymentAccounts.stream().findFirst()
                .orElseThrow(() -> new InsufficientFundsException("Fondos insuficientes en las cuentas de pago del usuario"));

        // Descuenta el saldo
        account.deductBalance(cost);
        paymentAccountRepository.save(account);
    }

    @Transactional
    public void sendNotification(long id, String message){
        Optional<User> user = userRepository.findById(id);
        // TODO Lógica para enviar un correo electrónico
        System.out.println("Enviando correo a " + user.get().getEmail() + ": " + message);
    }

    @Transactional
    public void toggleAccountStatus(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        user.setActive(!user.isActive());
        userRepository.save(user);
    }

    public List<ScooterLocation> findNearbyScooters(double latitude, double longitude, double radius) {
        return mapFeignClient.findScootersNearby(latitude, longitude, radius);
    }

    public TripResponseDto startTrip(Long userId, Long scooterId) {
        scooterFeignClient.activateScooter(scooterId);
        return tripFeignClient.createTrip(userId, scooterId);
    }
    public ResponseEntity<?> stopTrip (Long tripId, Long scooterId){
        scooterFeignClient.stopScooter(scooterId);
        return tripFeignClient.endTrip(tripId, scooterId);
    }
    /*******************************************************************************/

    private User convertDtoToEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setCellphone(userDto.getCellphone());
        return user;
    }

    private UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setCellphone(user.getCellphone());
        return userDto;
    }
}
