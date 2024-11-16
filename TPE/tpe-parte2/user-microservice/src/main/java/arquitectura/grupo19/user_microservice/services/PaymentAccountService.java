package arquitectura.grupo19.user_microservice.services;

import arquitectura.grupo19.user_microservice.dto.PaymentAccountDto;
import arquitectura.grupo19.user_microservice.dto.UserDto;
import arquitectura.grupo19.user_microservice.entities.PaymentAccount;
import arquitectura.grupo19.user_microservice.entities.User;
import arquitectura.grupo19.user_microservice.exceptions.NotFoundException;
import arquitectura.grupo19.user_microservice.repositories.PaymentAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentAccountService {

    private final PaymentAccountRepository paymentAccountRepository;

    public PaymentAccountService(PaymentAccountRepository paymentAccountRepository) {
        this.paymentAccountRepository = paymentAccountRepository;
    }

    @Transactional(readOnly = true)
    public List<PaymentAccountDto> getPaymentAccounts(){
        return paymentAccountRepository.findAll()
                .stream().map(PaymentAccountDto::new).toList();
    }

    @Transactional(readOnly = true) // Para que no guarde el estado y tengamos un mejor rendimiento de la consulta.
    public PaymentAccountDto getPaymentAccountById(Long id){
        return paymentAccountRepository.findById(id)
                .map(PaymentAccountDto::new)
                .orElseThrow(()->new NotFoundException("Payment Account", id));
    }

    @Transactional
    public PaymentAccountDto savePaymentAccount(PaymentAccountDto paymentAccountDto){
        PaymentAccount paymentAccount = convertDtoToEntity(paymentAccountDto);
        PaymentAccount save = paymentAccountRepository.save(paymentAccount);
        return convertEntityToDto(save);
    }

    private PaymentAccount convertDtoToEntity(PaymentAccountDto pADto) {
        PaymentAccount pA = new PaymentAccount();
        pA.setBalance(pADto.getBalance());
        pA.setDischargeDate(pADto.getDischargeDate());
        return pA;
    }

    private PaymentAccountDto convertEntityToDto(PaymentAccount pA) {
        PaymentAccountDto pADto = new PaymentAccountDto();
        pADto.setBalance(pA.getBalance());
        pADto.setDischargeDate(pA.getDischargeDate());
        return pADto;
    }
}
