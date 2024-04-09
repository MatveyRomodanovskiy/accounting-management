package telran.accounting.service;

import telran.accounting.dto.AccountDto;

public interface AccountService {
	AccountDto addAccount(AccountDto accountDto);
	AccountDto removeAccount(String email);
	void updatePassword(String email, String newPassword);

}
