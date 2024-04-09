package telran.accounting.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.accounting.model.Account;

public interface AccountRepo extends MongoRepository<Account, String> {



}
