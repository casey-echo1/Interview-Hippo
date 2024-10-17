package com.example.interviewhippo.repository;

import com.example.interviewhippo.model.Answer;
import com.example.interviewhippo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	List<Answer> findByUserOrderByCreatedAtDesc(User user);

	@Query("SELECT DISTINCT a.question.id FROM Answer a WHERE a.user = :user")
	List<Long> findQuestionIdsByUser(@Param("user") User user);

	@Query("SELECT a FROM Answer a WHERE SIZE(a.reviews) < 3")
	List<Answer> findAnswersWithFewerThanThreeReviews();
}
