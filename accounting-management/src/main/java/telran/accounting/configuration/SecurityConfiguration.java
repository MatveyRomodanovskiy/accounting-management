package telran.accounting.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import lombok.extern.slf4j.Slf4j;
@Slf4j
@Configuration
public class SecurityConfiguration {
	@Value("${app.password.strength:10}")
	int strength;
	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder(strength);
	}
	@Bean
	SecurityFilterChain configureChain (HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors(customizer ->customizer.disable())
			.csrf(customizer ->customizer.disable())
			.authorizeHttpRequests(authorize -> authorize
					.requestMatchers(HttpMethod.DELETE).hasRole("USER")
					.requestMatchers(HttpMethod.POST).hasRole("ADMIN")
					.requestMatchers("/update**").authenticated().anyRequest().permitAll())
			.httpBasic(Customizer.withDefaults());
		return httpSecurity.build();
	}
}
