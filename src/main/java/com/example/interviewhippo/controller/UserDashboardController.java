package com.example.interviewhippo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class UserDashboardController {

	private static final Logger logger = LoggerFactory.getLogger(UserDashboardController.class);

	@GetMapping("/user/dashboard")
	public String dashboard(Model model, Principal principal) {
		logger.info("Entering dashboard method");
		try {
			logger.info("Principal: {}", principal);
			if (principal == null) {
				logger.error("Principal is null");
				return "redirect:/login";
			}
			String username = principal.getName();
			logger.info("User {} accessed the dashboard", username);
			model.addAttribute("username", username);
			logger.info("Returning user-dashboard view");
			return "user/dashboard";
		} catch (Exception e) {
			logger.error("Error in dashboard method", e);
			throw e; // Re-throw the exception to be caught by GlobalExceptionHandler
		}
	}
}
