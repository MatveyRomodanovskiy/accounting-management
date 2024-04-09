package telran.accounting.controller;
import java.security.Principal;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.accounting.dto.AccountDto;
import telran.accounting.dto.PasswordUpdateDataDto;
import telran.accounting.service.AccountService;





@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountingController {
	final AccountService accountService;
	
	@PostMapping()
	AccountDto addAccount (@RequestBody @Valid AccountDto accountDto) {
		return accountService.addAccount(accountDto);	
	}

	@DeleteMapping("{mail}")
	AccountDto deleteAccount (@PathVariable String mail, Principal principal) {
		if(!principal.getName().equalsIgnoreCase(mail)) {
			throw new IllegalArgumentException("Only current authenticated user can delete own account");
		}
		return accountService.removeAccount(mail);	
	}
	
	@PutMapping("/update")
	void updatePassword(@RequestBody @Valid PasswordUpdateDataDto updateDataDto, Principal principal) {
		log.debug("enter to the update");
		if(!principal.getName().equalsIgnoreCase(updateDataDto.email())) {
			log.debug("Names principal and emails {} {}",principal.getName(), updateDataDto.email());
			throw new IllegalArgumentException("Only current authenticated user can update own account");
		}
		log.debug("Names principal and emails {} {}",updateDataDto.email());
		accountService.updatePassword(updateDataDto.email(), updateDataDto.password());
	}
	
	
}
