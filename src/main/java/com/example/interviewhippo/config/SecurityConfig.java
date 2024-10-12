package com.example.interviewhippo.config;

import com.example.interviewhippo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/css/**", "/js/**", "/images/**", "/login", "/register").permitAll()
				.requestMatchers("/api/admin/**", "/admin/**").hasRole("ADMIN")
				.requestMatchers("/interview/questions").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/api/user/**").hasRole("USER")
				.requestMatchers("/interview/questions").authenticated()
				.anyRequest().authenticated())
			.formLogin(form -> form
				.loginPage("/login")
				.permitAll()
				.successHandler((request, response, authentication) -> {
					if (authentication.getAuthorities().stream()
						.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
						response.sendRedirect("/admin/dashboard");
					} else {
						response.sendRedirect("/interview/questions");
					}
				})
			)
			.logout(logout -> logout
				.logoutSuccessUrl("/login")
				.permitAll()
			)
			.csrf(csrf -> csrf
				.ignoringRequestMatchers("/api/**")  // Disable CSRF for API endpoints
			)
			.userDetailsService(customUserDetailsService);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
