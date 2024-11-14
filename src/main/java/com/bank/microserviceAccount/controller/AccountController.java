package com.bank.microserviceAccount.controller;


import com.bank.microserviceAccount.Model.api.account.BankAccountDto;
import com.bank.microserviceAccount.Model.api.account.BankAccountRequest;
import com.bank.microserviceAccount.Model.api.shared.ResponseDto;
import com.bank.microserviceAccount.Model.api.shared.ResponseDtoBuilder;
import com.bank.microserviceAccount.business.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {


    private final IAccountService bankAccountService;

    @PostMapping("/accounts")
    public Mono<ResponseDto<BankAccountDto>> createBankAccount(@RequestBody BankAccountRequest request) {
        return bankAccountService.createBankAccount(request)
                .map(account -> ResponseDtoBuilder.success(account, "Cuenta bancaria creada con éxito"))
                .onErrorResume(error -> Mono.just(ResponseDtoBuilder.error(error.getMessage())));
    }

    @GetMapping("/{id}")
    public Mono<ResponseDto<BankAccountDto>> getAccountById(@PathVariable String id) {
        return bankAccountService.findById(id)
                .map(account -> ResponseDtoBuilder.success(account, "Cuenta bancaria encontrada"))
                .defaultIfEmpty(ResponseDtoBuilder.notFound("Cuenta bancaria no encontrada"));
    }

    @GetMapping
    public Flux<ResponseDto<BankAccountDto>> getAllAccounts() {
        return bankAccountService.findAll()
                .map(account -> ResponseDtoBuilder.success(account, "Cuenta bancaria obtenida"));
    }

    @PutMapping("/{id}")
    public Mono<ResponseDto<BankAccountDto>> updateAccount(@PathVariable String id, @RequestBody BankAccountRequest request) {
        return bankAccountService.updateBankAccount(id, request)
                .map(updatedAccount -> ResponseDtoBuilder.success(updatedAccount, "Cuenta bancaria actualizada"))
                .defaultIfEmpty(ResponseDtoBuilder.notFound("Cuenta bancaria no encontrada"))
                .onErrorResume(e -> Mono.just(ResponseDtoBuilder.error(e.getMessage())));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseDto<Object>> deleteAccount(@PathVariable String id) {
        return bankAccountService.deleteById(id)
                .then(Mono.just(ResponseDtoBuilder.success(null, "Cuenta bancaria eliminada")))
                .defaultIfEmpty(ResponseDtoBuilder.notFound("Cuenta bancaria no encontrada"));
    }

}
