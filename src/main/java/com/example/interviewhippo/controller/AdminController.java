package com.example.interviewhippo.controller;

import com.example.interviewhippo.model.Question;
import com.example.interviewhippo.model.User;
import com.example.interviewhippo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

//	@GetMapping("/dashboard")
//	public String dashboard() {
//		return "admin/dashboard";
//	}

	@GetMapping("/dashboard")
	public String showDashboard(Model model) {
		model.addAttribute("newQuestion", new Question());
		model.addAttribute("questions", adminService.getAllQuestions());
		return "admin/dashboard";
	}



	@PostMapping("/questions/add")
	public String addQuestion(@ModelAttribute("newQuestion") Question question) {
		adminService.submitNewQuestion(question);
		return "redirect:/admin/dashboard";
	}

	@PostMapping("/questions")
	public ResponseEntity submitNewQuestion(@RequestBody Question question) {
		return ResponseEntity.ok(adminService.submitNewQuestion(question));
	}
	@GetMapping("/questions")
	public ResponseEntity<List<Question>> getAllQuestions() {
		return ResponseEntity.ok(adminService.getAllQuestions());
	}
//	@GetMapping("/questions/{id}")
//	public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
//		return ResponseEntity.ok(adminService.getQuestionById(id));
//	}
	@PostMapping("/questions/edit/{id}")
	public ResponseEntity<Question> updateQuestion(@PathVariable Long id) {
		return ResponseEntity.ok(adminService.updateQuestion(id));
	}
	@PostMapping("/questions/delete/{id}")
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

	@GetMapping("/dashboard/users/count")
	public ResponseEntity<Long> getTotalUsersCount() {
		return ResponseEntity.ok(adminService.getTotalUsersCount());
	}

	@GetMapping("/dashboard/questions/count")
	public ResponseEntity<Long> getTotalQuestionsCount() {
		return ResponseEntity.ok(adminService.getTotalQuestionsCount());
	}

	@GetMapping("/dashboard/questions/category-count")
	public ResponseEntity<Map<String, Long>> getQuestionCountByCategory() {
		return ResponseEntity.ok(adminService.getQuestionCountByCategory());
	}
}
