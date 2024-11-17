package arquitectura.grupo19.user_microservice.controllers;

import arquitectura.grupo19.user_microservice.dto.PaymentAccountDto;
import arquitectura.grupo19.user_microservice.dto.UserDto;
import arquitectura.grupo19.user_microservice.services.PaymentAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("payment-accounts")
public class PaymentAccountController {

    private final PaymentAccountService paymentAccountService;

    public PaymentAccountController(PaymentAccountService paymentAccountService) {
        this.paymentAccountService = paymentAccountService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentAccountDto> getPaymentAccount() {
        return paymentAccountService.getPaymentAccounts();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void savePaymentAccount(@RequestBody PaymentAccountDto paymentAccountDto) {
        paymentAccountService.savePaymentAccount(paymentAccountDto);
    }

}
