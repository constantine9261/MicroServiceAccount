package com.bank.microserviceAccount.business.service.impl;
import com.bank.microserviceAccount.Model.api.account.BankAccountDto;
import com.bank.microserviceAccount.Model.api.account.BankAccountRequest;
import com.bank.microserviceAccount.Model.entity.AccountEntity;
import com.bank.microserviceAccount.business.repository.IAccountRepository;
import com.bank.microserviceAccount.business.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final IAccountRepository bankAccountRepository;
    private final WebClient customerWebClient;

    private AccountEntity convertToEntity(BankAccountRequest request) {
        return AccountEntity.builder()
                .accountNumber(request.getAccountNumber())
                .customerId(request.getCustomerId())
                .type(request.getType())
                .balance(request.getBalance())
                .maxTransactions(request.getMaxTransactions())
                .monthlyFee(request.getMonthlyFee())
                .allowedWithdrawalDate(request.getAllowedWithdrawalDate())
                .build();
    }

    private BankAccountDto convertToDto(AccountEntity entity) {
        return BankAccountDto.builder()
                .id(entity.getId())
                .accountNumber(entity.getAccountNumber())
                .customerId(entity.getCustomerId())
                .type(entity.getType())
                .balance(entity.getBalance())
                .maxTransactions(entity.getMaxTransactions())
                .monthlyFee(entity.getMonthlyFee())
                .allowedWithdrawalDate(entity.getAllowedWithdrawalDate())
                .build();
    }

    private Mono<Boolean> verifyCustomerExists(String customerId) {
        return customerWebClient.get()
                .uri("/{id}", customerId)
                .retrieve()
                .bodyToMono(Object.class)
                .map(response -> true)
                .onErrorResume(error -> Mono.just(false));
    }

    @Override
    public Mono<BankAccountDto> createBankAccount(BankAccountRequest request) {
        return verifyCustomerExists(request.getCustomerId())
                .flatMap(customerExists -> {
                    if (!customerExists) {
                        // Especifica que este Mono.error es de tipo BankAccountDto
                        return Mono.<BankAccountDto>error(new IllegalArgumentException("Cliente no válido"));
                    }
                    return bankAccountRepository.findByAccountNumber(request.getAccountNumber())
                            .flatMap(existingAccount ->
                                    Mono.<BankAccountDto>error(new IllegalStateException("Número de cuenta ya existe"))
                            )
                            .switchIfEmpty(Mono.defer(() -> {
                                AccountEntity accountEntity = convertToEntity(request);
                                return bankAccountRepository.save(accountEntity)
                                        .map(this::convertToDto); // Conversión a BankAccountDto después de guardar
                            }));
                });
    }

    @Override
    public Mono<BankAccountDto> findById(String id) {
        return bankAccountRepository.findById(id)
                .map(this::convertToDto)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cuenta no encontrada")));
    }

    @Override
    public Flux<BankAccountDto> findAll() {
        return bankAccountRepository.findAll()
                .map(this::convertToDto);
    }

    @Override
    public Mono<BankAccountDto> updateBankAccount(String id, BankAccountRequest request) {
        return verifyCustomerExists(request.getCustomerId())
                .flatMap(customerExists -> {
                    if (!customerExists) {
                        return Mono.error(new IllegalArgumentException("Cliente no válido"));
                    }
                    return bankAccountRepository.findById(id)
                            .switchIfEmpty(Mono.error(new IllegalArgumentException("Cuenta no encontrada")))
                            .flatMap(existingAccount -> {
                                existingAccount.setAccountNumber(request.getAccountNumber());
                                existingAccount.setBalance(request.getBalance());
                                existingAccount.setType(request.getType());
                                existingAccount.setMaxTransactions(request.getMaxTransactions());
                                existingAccount.setMonthlyFee(request.getMonthlyFee());
                                existingAccount.setAllowedWithdrawalDate(request.getAllowedWithdrawalDate());

                                return bankAccountRepository.save(existingAccount)
                                        .map(this::convertToDto); // Conversión solo después de la operación de repositorio
                            });
                });
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return bankAccountRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cuenta no encontrada")))
                .flatMap(bankAccountRepository::delete);
    }
}
