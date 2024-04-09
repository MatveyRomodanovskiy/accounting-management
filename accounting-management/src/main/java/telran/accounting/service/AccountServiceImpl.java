package telran.accounting.service;


import telran.exceptions.NotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.accounting.dto.AccountDto;
import telran.accounting.model.Account;
import telran.accounting.repo.AccountRepo;



@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
	final AccountRepo accountRepo;
	final PasswordEncoder passwordEncoder;
	@Override
	public AccountDto addAccount(AccountDto accountDto) {
		String idString = accountDto.email();
		AccountDto encodeAccountDto = getEncoded(accountDto);
		if (accountRepo.existsById(idString)){
			log.error("account with id: {} already exists",idString);
			throw new IllegalStateException(String.format("account with id: %s already exists", idString));
		}
		Account account = accountRepo.save(Account.of(encodeAccountDto));
		log.debug("account with id: {} has been added ", idString);
		return account.build();
	}
	

	private AccountDto getEncoded(AccountDto accountDto) {
		return new AccountDto(accountDto.email(), passwordEncoder.encode(accountDto.password()), accountDto.roles());
	}


	@Override
	public AccountDto removeAccount(String email) {
			Account account = accountRepo.findById(email)
					.orElseThrow(() -> new NotFoundException (String.format("account with id: %s doesn't exists", email)));
			accountRepo.deleteById(email);
			log.debug("account with id: {} has been removed", email);
		return account.build();
	}



}
