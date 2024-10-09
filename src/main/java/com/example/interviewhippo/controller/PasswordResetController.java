package com.example.interviewhippo.controller;

import com.example.interviewhippo.service.UserService;
import com.example.interviewhippo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/password-reset")
public class PasswordResetController {

	@Autowired
	private UserService userService;

	@PostMapping("/request")
	public ResponseEntity<String> resetPassword(@RequestParam("email") String userEmail) {
		Optional<User> userOptional = userService.findByEmail(userEmail);
		if (userOptional.isEmpty()) {
			return ResponseEntity.badRequest().body("User not found");
		}
		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(userOptional, token);
		// Here you would typically send an email with the reset link
		return ResponseEntity.ok("Reset password link sent to email");
	}

	@PostMapping("/change")
	public String changePassword(@RequestParam("token") String token, @RequestParam("password") String newPassword) {
		String result = userService.validatePasswordResetToken(token);
		if(result != null) {
			return "Invalid token";
		}
		User user = userService.getUserByPasswordResetToken(token);
		if(user == null) {
			return "Invalid token";
		}
		userService.changeUserPassword(user, newPassword);
		return "Password changed successfully";
	}
}
