package com.example.interviewhippo.controller_test;

import com.example.interviewhippo.controller.PracticeController;
import com.example.interviewhippo.model.Question;
import com.example.interviewhippo.model.User;
import com.example.interviewhippo.service.QuestionService;
import com.example.interviewhippo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PracticeControllerTest {

	@Mock
	private QuestionService questionService;

	@Mock
	private UserService userService;

	@Mock
	private Model model;

	@Mock
	private Principal principal;

	@InjectMocks
	private PracticeController practiceController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testPracticeForAnsweringUser() {
		User user = new User();
		user.setCurrentState(User.UserState.ANSWERING);
		Question question = new Question();

		when(principal.getName()).thenReturn("test@example.com");
		when(userService.getUserByEmail("test@example.com")).thenReturn(user);
		when(questionService.getQuestionForUser(user)).thenReturn(question);

		String viewName = practiceController.practice(model, principal);

		assertEquals("user/practice", viewName);
		verify(model).addAttribute("question", question);
		verify(model).addAttribute("userState", User.UserState.ANSWERING);
		verify(model).addAttribute("message", "Please answer the following question:");
	}

	@Test
	void testPracticeForReviewingUser() {
		User user = new User();
		user.setCurrentState(User.UserState.REVIEWING);
		Question question = new Question();

		when(principal.getName()).thenReturn("test@example.com");
		when(userService.getUserByEmail("test@example.com")).thenReturn(user);
		when(questionService.getQuestionForUser(user)).thenReturn(question);

		String viewName = practiceController.practice(model, principal);

		assertEquals("user/practice", viewName);
		verify(model).addAttribute("question", question);
		verify(model).addAttribute("userState", User.UserState.REVIEWING);
		verify(model).addAttribute("message", "Please review the following answer:");
	}
}
