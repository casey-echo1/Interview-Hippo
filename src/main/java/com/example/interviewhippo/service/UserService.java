package com.example.interviewhippo.service;

import com.example.interviewhippo.model.User;
import com.example.interviewhippo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User registerUser(User user) {
		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
			throw new RuntimeException("Username is already taken");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));
	}

	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found."));
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User updateUser(User user) {
		User existingUser = getUserById(user.getId());
		existingUser.setUsername(user.getUsername());
		existingUser.setEmail(user.getEmail());

		if(user.getPassword() != null && !user.getPassword().isEmpty()) {
			existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		return userRepository.save(existingUser);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public boolean verifyPassword(User user, String password) {
		return user.getPassword().equals(passwordEncoder.encode(password));
	}
}
