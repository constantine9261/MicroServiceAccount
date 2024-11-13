package com.bank.microserviceAccount.business.repository;


import com.bank.microserviceAccount.Model.entity.AccountEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IAccountRepository extends
        ReactiveMongoRepository<AccountEntity, Long> {

}
