package com.example.interviewhippo.service;

import com.example.interviewhippo.model.PasswordResetToken;
import com.example.interviewhippo.model.User;
import com.example.interviewhippo.repository.PasswordResetTokenRepository;
import com.example.interviewhippo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// creating new user
	public User createUser(User user) {
		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new RuntimeException("Email is already taken");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}



//	// TODO make sure that people can login with email and password first
//	public User getUserByUsername(String username) {
//		return userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found."));
//	}

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

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void createPasswordResetTokenForUser(Optional<User> userOptional, String token) {
		userOptional.ifPresent(user -> {
			PasswordResetToken myToken = new PasswordResetToken(token, user);
			passwordResetTokenRepository.save(myToken);
		});
	}

	public User getUserByPasswordResetToken(String token) {
		PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
		return passwordResetToken != null ? passwordResetToken.getUser() : null;
	}

	public String validatePasswordResetToken(String token) {
		PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);

		if (passToken == null) {
			return "invalidToken";
		}

		Calendar cal = Calendar.getInstance();
		if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			passwordResetTokenRepository.delete(passToken);
			return "expired";
		}

		return null;
	}

	public void changeUserPassword(User user, String password) {
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
	}
	public User getUserById(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("User not found with id: " + id));
	}

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("User not found with email: " + email));
	}
}
