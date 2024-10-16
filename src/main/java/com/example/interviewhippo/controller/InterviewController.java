package com.example.interviewhippo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.interviewhippo.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class InterviewController {

	private static final Logger logger = LoggerFactory.getLogger(InterviewController.class);

	private final QuestionService questionService;

	public InterviewController(QuestionService questionService) {
		this.questionService = questionService;
	}

	@GetMapping("/interview/questions")
	public String showInterviewQuestions(Model model) {
		logger.info("Accessing /interview/questions");
		try {
			model.addAttribute("questions", questionService.getAllQuestions());
			return "interview-questions";
		} catch (Exception e) {
			logger.error("Error in showInterviewQuestions", e);
			throw e;
		}
	}
}
