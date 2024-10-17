package com.example.interviewhippo.service_test;

import com.example.interviewhippo.model.User;
import com.example.interviewhippo.repository.UserRepository;
import com.example.interviewhippo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateUser() {
		User user = new User();
		user.setEmail("test@example.com");
		user.setPassword("password");

		when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
		when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
		when(userRepository.save(any(User.class))).thenReturn(user);

		User createdUser = userService.createUser(user);

		assertNotNull(createdUser);
		assertEquals("encodedPassword", createdUser.getPassword());
		verify(userRepository).save(user);
	}

	@Test
	void testCreateUserWithExistingEmail() {
		User user = new User();
		user.setEmail("test@example.com");

		when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));

		assertThrows(RuntimeException.class, () -> userService.createUser(user));
	}

	@Test
	void testGetUserByEmail() {
		User user = new User();
		user.setEmail("test@example.com");

		when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

		User result = userService.getUserByEmail("test@example.com");

		assertEquals(user, result);
	}

	@Test
	void testGetUserByEmailNotFound() {
		when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> userService.getUserByEmail("test@example.com"));
	}
}
