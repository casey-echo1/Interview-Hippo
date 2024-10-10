package com.example.interviewhippo.controller;

import com.example.interviewhippo.model.Question;
import com.example.interviewhippo.model.User;
import com.example.interviewhippo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("/questions")
	public ResponseEntity submitNewQuestion(@RequestBody Question question) {
		return ResponseEntity.ok(adminService.submitNewQuestion(question));
	}
	@GetMapping("/questions")
	public ResponseEntity<List<Question>> getAllQuestions() {
		return ResponseEntity.ok(adminService.getAllQuestions());
	}
	@GetMapping("/questions/{id}")
	public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
		return ResponseEntity.ok(adminService.getQuestionById(id));
	}
	@PutMapping("/questions/{id}")
	public ResponseEntity<Question> updateQuestion(@PathVariable Long id) {
		return ResponseEntity.ok(adminService.updateQuestion(id));
	}
	@DeleteMapping("/questions/{id}")
	public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
		adminService.deleteQuestion(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(adminService.getAllUsers());
	}

	@PostMapping("/users/{userId}/make-admin")
	public ResponseEntity<User> makeUserAdmin(@PathVariable Long userId) {
		return ResponseEntity.ok(adminService.assignAdminRole(userId));
	}

	@PostMapping("/users/{userId}/remove-admin")
	public ResponseEntity<User> removeAdminRole(@PathVariable Long userId) {
		return ResponseEntity.ok(adminService.removeAdminRole(userId));
	}


}
