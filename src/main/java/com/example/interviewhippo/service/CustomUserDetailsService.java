package com.example.interviewhippo.service;

import com.example.interviewhippo.model.User;
import com.example.interviewhippo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		logger.info("Attempting to load user by email: {}", email);
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> {
				logger.error("User not found with email: {}", email);
				return new UsernameNotFoundException("User not found with email: " + email);
			});

		logger.info("User found: {}", user.getEmail());
		String roleWithPrefix = "ROLE_" + user.getRole().name();
		logger.info("User role: {}", roleWithPrefix);

		return org.springframework.security.core.userdetails.User
			.withUsername(user.getEmail())
			.password(user.getPassword())
			.authorities(Collections.singletonList(new SimpleGrantedAuthority(roleWithPrefix)))
			.build();
	}
}
