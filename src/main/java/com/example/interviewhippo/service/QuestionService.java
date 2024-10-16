package com.example.interviewhippo.service;

import com.example.interviewhippo.model.Question;
import com.example.interviewhippo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

	private final QuestionRepository questionRepository;

	@Autowired
	public QuestionService(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}

	public List<Question> getAllQuestions() {
		return questionRepository.findAll();
	}

	public Question getRandomQuestion() {
		long qty = questionRepository.count();
		int idx = (int)(Math.random() * qty);
		Page<Question> questionPage = questionRepository.findAll(PageRequest.of(idx, 1));
		return questionPage.getContent().getFirst();
	}
}
