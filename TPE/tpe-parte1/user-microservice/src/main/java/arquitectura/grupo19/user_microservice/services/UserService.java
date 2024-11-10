package arquitectura.grupo19.user_microservice.services;

import arquitectura.grupo19.user_microservice.dto.UserDto;
import arquitectura.grupo19.user_microservice.entities.PaymentAccount;
import arquitectura.grupo19.user_microservice.entities.User;
import arquitectura.grupo19.user_microservice.repositories.UserRepository;
import arquitectura.grupo19.user_microservice.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    //private final String tripServiceUrl = "http://api-gateway/trip-microservice/api/trips";

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean hasSufficientBalance(long id, double cost){
        Optional<User> user = userRepository.findById(id);
        List<PaymentAccount> paymentAccounts =  user.get().getPaymentAccounts();
        for(PaymentAccount mp: paymentAccounts){
            if(mp.hasSufficientBalance(cost)) return true;
        }
        return false;
    }

    public void notify(long id, String message){
        System.out.println(message);
    }

    /*public boolean hasBalance(long id){
        Optional<User> user = userRepository.findById(id);
        List<PaymentAccount> paymentAccounts =  user.get().getPaymentAccounts();
        for(PaymentAccount mp: paymentAccounts){
            if(mp.hasBalance()) return true;
        }
        return false;
    }*/

    public void activateScooter(long userId, long scooterId){
       /* Optional<User> user = userRepository.findById(userId);
        List<PaymentAccount> paymentAccounts =  user.get().getPaymentAccounts();
        for(PaymentAccount mp: paymentAccounts){
            if(mp.tieneSaldo()){
                if(!trip.viajando()){
                    this.userRepository.activateScooter(userId, scooterId);
                    //trip.generarViaje()
                    //mp.descontar()
                } else {
                    //continuar servicio con otra cuenta de mercado pago
                }
            }
        }*/
    }

    @Transactional
    public UserDto saveUser(UserDto userDto){
        // Convertir el DTO a entidad User
        User user = convertDtoToEntity(userDto);

        // Si pasa las validaciones, guardar el estudiante en base de datos
        User save = userRepository.save(user);

        // Retornar DTO
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

    private User convertDtoToEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFistName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setCellphone(userDto.getCellphone());
        //user.setPaymentMethod(userDto.getPaymentMethod());
        return user;
    }

    private UserDto convertEntityToDto(User usuario) {
        UserDto userDto = new UserDto();
        userDto.setUsername(usuario.getUsername());
        userDto.setFirstName(usuario.getFistName());
        userDto.setLastName(usuario.getLastName());
        userDto.setEmail(usuario.getEmail());
        userDto.setCellphone(usuario.getCellphone());
        //userDto.setPaymentMethod(usuario.getPaymentMethod());
        return userDto;
    }
}
