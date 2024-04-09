package telran.accounting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class BiCryptTest {
	PasswordEncoder encoder;

	@Test
	@Disabled
	void biCrypttest() {
		encoder = new BCryptPasswordEncoder(16);
		String passwordString = "user1234";
		String hashPass = encoder.encode(passwordString);
		assertTrue(encoder.matches(passwordString, hashPass));
		assertFalse(encoder.matches(passwordString, "kuku"));
	}

}
