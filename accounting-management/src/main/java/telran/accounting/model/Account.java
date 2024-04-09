package telran.accounting.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telran.accounting.dto.AccountDto;

@Document(collection="accounts")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	@Id
	String email;
	String hashPassword;
	String[] roles;
	
	public static Account of(AccountDto accountDto) {
		return new Account(accountDto.email(), accountDto.password(), accountDto.roles());
	}
	public AccountDto build() {
		return new AccountDto(email, "********", roles);
	}

}
