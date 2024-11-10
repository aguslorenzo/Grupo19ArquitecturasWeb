package arquitectura.grupo19.user_microservice.services;

import arquitectura.grupo19.user_microservice.dto.PaymentAccountDto;
import arquitectura.grupo19.user_microservice.entities.PaymentAccount;
import arquitectura.grupo19.user_microservice.repositories.PaymentAccountRepository;
import arquitectura.grupo19.user_microservice.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentAccountService {

    private final PaymentAccountRepository paymentAccountRepository;

    public PaymentAccountService(PaymentAccountRepository paymentAccountRepository) {
        this.paymentAccountRepository = paymentAccountRepository;
    }

    @Transactional
    public PaymentAccountDto savePaymentAccount(PaymentAccountDto paymentAccountDto){
        // Convertir el DTO a entidad User
        PaymentAccount paymentAccount = convertDtoToEntity(paymentAccountDto);

        // Si pasa las validaciones, guardar el estudiante en base de datos
        PaymentAccount save = paymentAccountRepository.save(paymentAccount);

        // Retornar DTO
        return convertEntityToDto(save);
    }

    @Transactional(readOnly = true) // Para que no guarde el estado y tengamos un mejor rendimiento de la consulta.
    public PaymentAccountDto getPaymentAccountById(Long id){
        return paymentAccountRepository.findById(id)
                .map(PaymentAccountDto::new)
                .orElseThrow(()->new NotFoundException("PaymentAccount", id));
    }

    @Transactional(readOnly = true)
    public List<PaymentAccountDto> getPaymentAccounts(){
        return paymentAccountRepository.findAll()
                .stream().map(PaymentAccountDto::new).toList();
    }

    public void updatePaymentAccount(Long id, PaymentAccountDto paymentAccountDto){
        PaymentAccount paymentAccount = paymentAccountRepository.findById(id)
                .orElseThrow(()->new NotFoundException("PaymentAccount", id));

        // TODO Actualizar los campos del usuario con los datos nuevos

        paymentAccountRepository.save(paymentAccount);
    }

    public PaymentAccountDto deletePaymentAccount(Long id){
        PaymentAccount paymentAccount = paymentAccountRepository.findById(id)
                .orElseThrow(()->new NotFoundException("PaymentAccount", id));
        paymentAccountRepository.delete(paymentAccount);
        return convertEntityToDto(paymentAccount);
    }

    private PaymentAccount convertDtoToEntity(PaymentAccountDto userDto) {
        PaymentAccount user = new PaymentAccount();
        // TODO hacer conversión
        return user;
    }

    private PaymentAccountDto convertEntityToDto(PaymentAccount paymentAccount) {
        PaymentAccountDto paymentAccountDto = new PaymentAccountDto();
        // TODO hacer conversión
        return paymentAccountDto;
    }
}
