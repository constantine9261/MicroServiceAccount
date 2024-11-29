package com.bank.microserviceAccount.business.repository;


import com.bank.microserviceAccount.Model.entity.AccountEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountRepository extends
        ReactiveMongoRepository<AccountEntity, String> {
    Mono<AccountEntity> findByAccountNumber(String accountNumber);

    // Método para buscar todas las cuentas de un cliente específico
    Flux<AccountEntity> findByCustomerId(String customerId);

}
