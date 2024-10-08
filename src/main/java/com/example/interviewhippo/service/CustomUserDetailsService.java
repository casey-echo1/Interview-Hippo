package com.example.interviewhippo.service;

import com.example.interviewhippo.model.User;
import com.example.interviewhippo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

		String roleWithPrefix = "ROLE_USER"; // Default role
		if (user.getRole() != null) {
			String role = user.getRole().name();
			roleWithPrefix = role.startsWith("ROLE_") ? role : "ROLE_" + role;
		}

		return org.springframework.security.core.userdetails.User
			.withUsername(user.getEmail())
			.password(user.getPassword())
			.authorities(Collections.singletonList(new SimpleGrantedAuthority(roleWithPrefix)))
			.build();
	}
}

