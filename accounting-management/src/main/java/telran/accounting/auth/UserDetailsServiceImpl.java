package telran.accounting.auth;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.accounting.model.Account;
import telran.accounting.repo.AccountRepo;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
final AccountRepo accountRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Account account = accountRepo.findById(email)
				.orElseThrow(()-> new UsernameNotFoundException(email));
		String[] roleStrings = Arrays.stream(account.getRoles()).map(r -> "ROLE_" + r).toArray(String[] :: new);
		return new User(email, account.getHashPassword(), AuthorityUtils.createAuthorityList(roleStrings));
}
}
