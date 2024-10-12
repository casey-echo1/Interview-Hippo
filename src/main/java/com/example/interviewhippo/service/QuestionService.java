package com.example.interviewhippo.service;

import com.example.interviewhippo.model.Question;
import com.example.interviewhippo.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

	private final QuestionRepository questionRepository;

	public QuestionService(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}

	public List<Question> getAllQuestions() {
		return questionRepository.findAll();
	}
}
