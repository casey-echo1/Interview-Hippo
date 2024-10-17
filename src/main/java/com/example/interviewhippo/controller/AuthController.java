package com.example.interviewhippo.controller;

import com.example.interviewhippo.model.Role;
import com.example.interviewhippo.model.User;
import com.example.interviewhippo.service.AdminService;
import com.example.interviewhippo.service.CustomUserDetailsService;
import com.example.interviewhippo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

	@Autowired
	private UserService userService;

	// Show the login form
	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}

	// Show the registration page
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		logger.info("Showing registration form");
		model.addAttribute("user", new User());
		model.addAttribute("allRoles", Role.values());  // This gives an array of all enum values
		return "register";
	}

	@PostMapping("/register")
	public String createUser(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "register";
		}
		try {
			userService.createUser(user);
			return "redirect:/login?registered";
		} catch (RuntimeException e) {
			model.addAttribute("error", "Registration failed. " + e.getMessage());
			return "register";
		}
	}


}
