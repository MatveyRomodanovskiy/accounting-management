package telran.accounting.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.accounting.dto.AccountDto;
import telran.accounting.service.AccountService;
import telran.exceptions.NotFoundException;




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
	AccountDto deleteAccount (@PathVariable String mail) {
		return accountService.removeAccount(mail);	
	}
	

	
	
}
