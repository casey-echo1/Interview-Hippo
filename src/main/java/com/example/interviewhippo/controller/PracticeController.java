package com.example.interviewhippo.controller;

import com.example.interviewhippo.model.Question;
import com.example.interviewhippo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PracticeController {
	@Autowired
	private QuestionService questionService;

	@GetMapping("/user/practice")
	public String practice(Model model) {
		Question randomQuestion = questionService.getRandomQuestion();
		model.addAttribute("question", randomQuestion);
		return "user/practice";
	}
}
