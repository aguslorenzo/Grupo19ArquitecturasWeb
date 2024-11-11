package arquitectura.grupo19.user_microservice.services;

import arquitectura.grupo19.user_microservice.dto.UserDto;
import arquitectura.grupo19.user_microservice.entities.PaymentAccount;
import arquitectura.grupo19.user_microservice.entities.User;
import arquitectura.grupo19.user_microservice.exceptions.InsufficientFundsException;
import arquitectura.grupo19.user_microservice.exceptions.UserNotFoundException;
import arquitectura.grupo19.user_microservice.repositories.PaymentAccountRepository;
import arquitectura.grupo19.user_microservice.repositories.UserRepository;
import arquitectura.grupo19.user_microservice.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PaymentAccountRepository paymentAccountRepository;

    public UserService(UserRepository userRepository, PaymentAccountRepository paymentAccountRepository) {
        this.userRepository = userRepository;
        this.paymentAccountRepository = paymentAccountRepository;
    }

    @Transactional
    public UserDto saveUser(UserDto userDto){
        User user = convertDtoToEntity(userDto);
        User save = userRepository.save(user);
        return convertEntityToDto(save);
    }

    @Transactional(readOnly = true) // Para que no guarde el estado y tengamos un mejor rendimiento de la consulta.
    public UserDto getUserById(Long id){
        return userRepository.findById(id)
                .map(UserDto::new)
                .orElseThrow(()->new NotFoundException("User", id));
    }

    @Transactional(readOnly = true)
    public List<UserDto> getUsers(){
        return userRepository.findAll()
                .stream().map(UserDto::new).toList();
    }

    public void updateUser(Long id, UserDto userDto){
        // Buscar usuario por id
        User user = userRepository.findById(id)
                .orElseThrow(()->new NotFoundException("User", id));

        // Actualizar los campos del usuario con los datos nuevos
        user.setUsername(userDto.getUsername());
        user.setFistName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setCellphone(userDto.getCellphone());
        //user.setPaymentMethod(userDto.getPaymentMethod());

        // Guardar los cambios
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

    /*******************************************************************************/

    private User convertDtoToEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFistName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setCellphone(userDto.getCellphone());
        user.setPaymentAccounts(userDto.getPaymentAccounts());
        return user;
    }

    private UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFistName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setCellphone(user.getCellphone());
        userDto.setPaymentAccounts(user.getPaymentAccounts());
        return userDto;
    }
}
