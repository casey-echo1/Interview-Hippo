package com.example.interviewhippo.config;

import com.example.interviewhippo.model.Role;
import com.example.interviewhippo.model.User;
import com.example.interviewhippo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/*
this class ensures that there is an admin profile for development/testing purposes
 */
@Component
public class AdminInitializer implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) {
		if (userRepository.count() == 0) {
			User admin = new User();
			admin.setUsername("admin");
			admin.setEmail("admin@example.com");
			admin.setPassword(passwordEncoder.encode("adminpassword"));
			admin.setRole(Role.ADMIN);
			admin.setEnabled(true);
			userRepository.save(admin);
			System.out.println("Admin user created.");
		}
	}
}
