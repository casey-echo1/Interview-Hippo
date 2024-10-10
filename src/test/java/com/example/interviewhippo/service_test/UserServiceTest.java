package com.example.interviewhippo.service_test;

import com.example.interviewhippo.model.User;
import com.example.interviewhippo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void testAdminUserExistsAndPasswordIsCorrect() {
		String email = "admin@example.com";
		String password = "adminpassword";

		Optional<User> userOptional = userRepository.findByEmail(email);

		assertTrue(userOptional.isPresent(), "Admin user should exist");

		User user = userOptional.get();
		assertTrue(passwordEncoder.matches(password, user.getPassword()), "Password should match");
	}
}
