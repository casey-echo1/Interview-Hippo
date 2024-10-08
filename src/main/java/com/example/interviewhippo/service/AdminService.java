package com.example.interviewhippo.service;

import com.example.interviewhippo.model.Question;
import com.example.interviewhippo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

	@Autowired
	private QuestionRepository questionRepository;

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
