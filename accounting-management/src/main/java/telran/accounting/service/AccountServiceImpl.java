package telran.accounting.service;


import telran.exceptions.NotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.accounting.dto.AccountDto;
import telran.accounting.model.Account;




@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
	final MongoTemplate mongoTemplate;
	final PasswordEncoder passwordEncoder;

	@Override
	public AccountDto addAccount(AccountDto accountDto) {
		String idString = accountDto.email();
		AccountDto encodeAccountDto = getEncoded(accountDto);
		Account account = null;
		try {
			account = mongoTemplate.insert(Account.of(encodeAccountDto));
			
		}catch (DuplicateKeyException e) {
			throw new IllegalStateException(String.format("account with id: %s already exists", idString));
		}
		
		log.debug("account with id: {} has been added ", idString);
		return account.build();
	}
	

	private AccountDto getEncoded(AccountDto accountDto) {
		return new AccountDto(accountDto.email(), passwordEncoder.encode(accountDto.password()), accountDto.roles());
	}


	@Override
	public AccountDto removeAccount(String email) {
			Account account = mongoTemplate.findAndRemove(new Query(Criteria.where("email")
					.is(email)), Account.class);
			if(account == null) {
				throw new NotFoundException (String.format("account with id: %s doesn't exists", email));
			}
			log.debug("account with id: {} has been removed", email);
		return account.build();
	}


	@Override
	public void updatePassword(String email, String newPassword) {
		Update update = new Update();
		update.set("hashPassword", passwordEncoder.encode(newPassword));
		Account account = mongoTemplate.findAndModify(new Query(Criteria.where("email").is(email)), update, Account.class);
		if (account == null) {
			throw new IllegalStateException (String.format("account with id: %s doesn't exists", email));
		}
		log.debug("account with id: {} has been successfully updated", email);
	}

}
