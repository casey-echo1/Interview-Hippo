package com.example.interviewhippo.controller;

import com.example.interviewhippo.model.User;
import com.example.interviewhippo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user) {
		return ResponseEntity.ok(userService.registerUser(user));
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
		return ResponseEntity.ok(userService.getUserByUsername(email));
	}

	@GetMapping("/username/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		return ResponseEntity.ok(userService.getUserByUsername(username));
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
		user.setId(id);
		return ResponseEntity.ok(userService.updateUser(user));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/verify")
	public ResponseEntity<Boolean> verifyPassword(@RequestParam String username, @RequestParam String password) {
		return ResponseEntity.ok(userService.verifyPassword(username, password));
	}

}
