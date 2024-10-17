package com.example.interviewhippo.service;

import com.example.interviewhippo.exception.NoAnswersAvailableException;
import com.example.interviewhippo.model.Answer;
import com.example.interviewhippo.model.Question;
import com.example.interviewhippo.model.Review;
import com.example.interviewhippo.model.User;
import com.example.interviewhippo.repository.AnswerRepository;
import com.example.interviewhippo.repository.QuestionRepository;
import com.example.interviewhippo.repository.ReviewRepository;
import com.example.interviewhippo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class QuestionService {

	private final QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private UserRepository userRepository;

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

	public Question getQuestionForUser(User user) {
		if (user.getCurrentState() == User.UserState.ANSWERING) {
			return getRandomUnansweredQuestion(user);
		} else {
			try {
				return getQuestionForReview(user);
			} catch (RuntimeException e) {
				// If no answers to review, switch user back to answering mode
				user.setCurrentState(User.UserState.ANSWERING);
				userRepository.save(user);
				return getRandomUnansweredQuestion(user);
			}
		}
	}

	private Question getRandomUnansweredQuestion(User user) {
		// Get all question IDs
		List<Long> allQuestionIds = questionRepository.findAllIds();
		// Get IDs of questions the user has already answered
		List<Long> answeredQuestionIds = answerRepository.findQuestionIdsByUser(user);
		// Remove answered questions from all questions
		allQuestionIds.removeAll(answeredQuestionIds);
		if (allQuestionIds.isEmpty()) {
			throw new RuntimeException("No unanswered questions available");
		}
		// Select a random question ID from the remaining questions
		Long randomQuestionId = allQuestionIds.get(new Random().nextInt(allQuestionIds.size()));
		return questionRepository.findById(randomQuestionId)
			.orElseThrow(() -> new RuntimeException("Question not found"));
	}

	private Question getQuestionForReview(User user) {
		List<Answer> answersNeedingReview = answerRepository.findAnswersWithFewerThanThreeReviews();
		answersNeedingReview.removeIf(answer -> answer.getUser().getId().equals(user.getId()));
		if (answersNeedingReview.isEmpty()) {
			throw new NoAnswersAvailableException("No answers available for review");
		}
		Answer randomAnswer = answersNeedingReview.get(new Random().nextInt(answersNeedingReview.size()));
		return randomAnswer.getQuestion();
	}

	@Transactional
	public void submitAnswer(User user, Question question, String answerContent) {
		Answer answer = new Answer();
		answer.setUser(user);
		answer.setQuestion(question);
		answer.setContent(answerContent);
		answer.setCreatedAt(LocalDateTime.now());
		answerRepository.save(answer);

		user.setCurrentState(User.UserState.REVIEWING);
		userRepository.save(user);

		// Optionally, update question stats
		question.incrementAnswerCount();
		questionRepository.save(question);
	}

	public void submitReview(User reviewer, Answer answer, int score, String comment) {
		Review review = new Review();
		review.setReviewer(reviewer);
		review.setAnswer(answer);
		review.setScore(score);
		review.setComment(comment);
		review.setCreatedAt(LocalDateTime.now());
		reviewRepository.save(review);

		reviewer.setCurrentState(User.UserState.ANSWERING);
		// Save user state change
	}

	public Question getQuestionById(Long id) {
		return questionRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
	}

	public Answer getAnswerById(Long id) {
		return answerRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Answer not found with id: " + id));
	}


}
