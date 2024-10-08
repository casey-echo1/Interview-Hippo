package com.example.interviewhippo.service;

import com.example.interviewhippo.model.User;
import com.example.interviewhippo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User registerUser(User user) {
		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new RuntimeException("Username is already taken");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));
	}

	public User getUserByUsername(String username) {
		return userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found."));
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User updateUser(User user) {
		User existingUser = getUserById(user.getId());
		existingUser.setEmail(user.getEmail());

		if(user.getPassword() != null && !user.getPassword().isEmpty()) {
			existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		return userRepository.save(existingUser);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public boolean verifyPassword(String username, String password) {
		User user = getUserByUsername(username);
		return user.getPassword().equals(passwordEncoder.encode(password));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

		List<GrantedAuthority> authorities = new ArrayList<>();
		if (user.isAdmin()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		return new org.springframework.security.core.userdetails.User(
			user.getEmail(),
			user.getPassword(),
			authorities
		);
	}
}
