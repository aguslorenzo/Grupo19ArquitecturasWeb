package arquitectura.grupo19.user_microservice.controllers;

import arquitectura.grupo19.user_microservice.dto.PaymentAccountDto;
import arquitectura.grupo19.user_microservice.services.PaymentAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/payments")
public class PaymentAccountController {

    private final PaymentAccountService paymentAccountService;

    public PaymentAccountController(PaymentAccountService paymentAccountService) {
        this.paymentAccountService = paymentAccountService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentAccountDto> getPaymentAccounts() {
        return paymentAccountService.getPaymentAccounts();
    }

    @GetMapping("/id/{id}")
    public PaymentAccountDto getPaymentAccountById(@PathVariable Long id) {
        return paymentAccountService.getPaymentAccountById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void savePaymentAccount(@RequestBody PaymentAccountDto paymentAccountDto) {
        paymentAccountService.savePaymentAccount(paymentAccountDto);
    }

    @PutMapping("/{id}")
    public void updatePaymentAccount(@PathVariable Long id, @RequestBody PaymentAccountDto paymentAccountDto) {
        paymentAccountService.updatePaymentAccount(id, paymentAccountDto);
    }

    @DeleteMapping("/{id}")
    public PaymentAccountDto deletePaymentAccount(@PathVariable Long id) {
        return paymentAccountService.deletePaymentAccount(id);
    }
}
