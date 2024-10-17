package com.example.interviewhippo.controller;

import com.example.interviewhippo.model.Answer;
import com.example.interviewhippo.model.Question;
import com.example.interviewhippo.model.User;
import com.example.interviewhippo.service.QuestionService;
import com.example.interviewhippo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;

@Controller
@RequestMapping("/user/practice")
public class PracticeController {

	private static final Logger logger = LoggerFactory.getLogger(PracticeController.class);


	@Autowired
	private QuestionService questionService;

	@Autowired
	private UserService userService;


	@GetMapping
	public String practice(Model model, Principal principal) {
		User user = userService.getUserByEmail(principal.getName());
		Question question = questionService.getQuestionForUser(user);
		model.addAttribute("question", question);
		model.addAttribute("userState", user.getCurrentState());
		if (user.getCurrentState() == User.UserState.ANSWERING) {
			model.addAttribute("message", "Please answer the following question:");
		} else {
			model.addAttribute("message", "Please review the following answer:");
			//TODO Add logic to fetch and add the answer to be reviewed
		}
		return "user/practice";
	}

	@PostMapping("/submit-answer")
	public String submitAnswer(@RequestParam Long questionId, @RequestParam String answerContent, Principal principal) {
		User user = userService.getUserByEmail(principal.getName());
		Question question = questionService.getQuestionById(questionId);
		questionService.submitAnswer(user, question, answerContent);
		return "redirect:/user/practice";
	}

	@PostMapping("/submit-review")
	public String submitReview(@RequestParam Long answerId, @RequestParam int score, @RequestParam String comment, Principal principal) {
		User reviewer = userService.getUserByEmail(principal.getName());
		Answer answer = questionService.getAnswerById(answerId);
		questionService.submitReview(reviewer, answer, score, comment);
		return "redirect:/user/practice";
	}
}
