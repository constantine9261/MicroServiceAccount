package com.bank.microserviceAccount.business.service;


import com.bank.microserviceAccount.Model.api.account.BankAccountDto;
import com.bank.microserviceAccount.Model.api.account.BankAccountRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountService {

    Mono<BankAccountDto> createBankAccount(BankAccountRequest request);
    Mono<BankAccountDto> findById(String id);
    Flux<BankAccountDto> findAll();
    Mono<BankAccountDto> updateBankAccount(String id, BankAccountRequest request);
    Mono<Void> deleteById(String id);

    Mono<BankAccountDto> createVipAccount(BankAccountRequest request);

    Mono<BankAccountDto> createPymeAccount(BankAccountRequest request);
}