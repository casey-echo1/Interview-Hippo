package com.example.interviewhippo.service;

import com.example.interviewhippo.model.Question;
import com.example.interviewhippo.model.Role;
import com.example.interviewhippo.model.User;
import com.example.interviewhippo.repository.QuestionRepository;
import com.example.interviewhippo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
This handles service for admin activities such as assigning admin role, submitting and editing
new questions to the database
 */
@Service
public class AdminService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User assignAdminRole(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("User not found"));
		user.setRole(Role.ADMIN);
		return userRepository.save(user);
	}

	public User removeAdminRole(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("User not found"));
		user.setRole(Role.USER);
		return userRepository.save(user);
	}

	public long getTotalUsersCount() {
		return userRepository.count();
	}

	public long getTotalQuestionsCount() {
		return questionRepository.count();
	}

	public Map<String, Long> getQuestionCountByCategory() {
		return questionRepository.findAll().stream()
			.collect(Collectors.groupingBy(Question::getCategory, Collectors.counting()));
	}


	public Question submitNewQuestion(Question question) {
		return questionRepository.save(question);
	}

	public List<Question> getAllQuestions() {
		return questionRepository.findAll();
	}

	public Question getQuestionById(Long id) {
		return questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
	}

	public Question updateQuestion(Long id) {
		return questionRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Question not found"));
	}

	public void deleteQuestion(Long id) {
		Question question = getQuestionById(id);
		questionRepository.delete(question);
	}
}
