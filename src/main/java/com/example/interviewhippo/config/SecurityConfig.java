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
import org.springframework.security.core.authority.AuthorityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/css/**", "/js/**", "/images/**","/login", "/register","/favicon.ico", "/error" ).permitAll()
				.requestMatchers("/api/admin/**", "/admin/**").hasRole("ADMIN")
				.requestMatchers("/api/user/**", "/user/**").hasAnyRole("USER", "ADMIN")
				.anyRequest().authenticated())
			.formLogin(form -> form
				.loginPage("/login")
				.permitAll()
				.successHandler((request, response, authentication) -> {
					logger.info("User {} successfully authenticated", authentication.getName());
					var authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
					if (authorities.contains("ROLE_ADMIN")) {
						response.sendRedirect("/admin/dashboard");
					} else {
						response.sendRedirect("/user/dashboard");
					}
				})
				.failureHandler((request, response, exception) -> {
					logger.error("Authentication failed: {}", exception.getMessage());
					response.sendRedirect("/login?error");
				})
			)
			.logout(logout -> logout
				.logoutSuccessUrl("/login")
				.permitAll())
			.csrf(csrf -> csrf
				.ignoringRequestMatchers("/api/**"))
			.userDetailsService(customUserDetailsService);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
