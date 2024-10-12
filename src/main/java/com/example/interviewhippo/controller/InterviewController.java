package com.example.interviewhippo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.interviewhippo.service.QuestionService;

@Controller
public class InterviewController {

	private final QuestionService questionService;

	public InterviewController(QuestionService questionService) {
		this.questionService = questionService;
	}

	@GetMapping("/interview/questions")
	public String showInterviewQuestions(Model model) {
		model.addAttribute("questions", questionService.getAllQuestions());
		return "interview-questions";
	}
}
