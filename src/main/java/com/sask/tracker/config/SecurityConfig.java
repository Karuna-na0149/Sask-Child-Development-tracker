package com.sask.tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				auth -> auth.requestMatchers("/user-login", "/signup", "/forgot-password", "/reset-password", "/error")
						.permitAll().requestMatchers("/images/**", "/css/**", "/js/**").permitAll()
						.requestMatchers("/admin/**").hasAuthority("ADMIN").requestMatchers("/parent/**")
						.hasAuthority("PARENT").anyRequest().authenticated())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/user-login"))
				.csrf(csrf -> csrf.disable()); // Disable CSRF for testing (optional)

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}