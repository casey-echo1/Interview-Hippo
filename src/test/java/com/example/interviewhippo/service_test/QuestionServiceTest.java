package com.example.interviewhippo.service_test;

import com.example.interviewhippo.model.Question;
import com.example.interviewhippo.model.User;
import com.example.interviewhippo.repository.QuestionRepository;
import com.example.interviewhippo.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class QuestionServiceTest {

	@Mock
	private QuestionRepository questionRepository;

	@InjectMocks
	private QuestionService questionService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllQuestions() {
		List<Question> questions = Arrays.asList(new Question(), new Question());
		when(questionRepository.findAll()).thenReturn(questions);

		List<Question> result = questionService.getAllQuestions();

		assertEquals(2, result.size());
		verify(questionRepository).findAll();
	}

	@Test
	void testGetQuestionForUser() {
		User user = new User();
		user.setCurrentState(User.UserState.ANSWERING);
		Question question = new Question();

		when(questionRepository.findAll()).thenReturn(List.of(question));

		Question result = questionService.getQuestionForUser(user);

		assertEquals(question, result);
	}
}
