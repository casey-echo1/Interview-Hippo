package com.example.interviewhippo.repository;

import com.example.interviewhippo.model.Review;
import com.example.interviewhippo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	List<Review> findByReviewerOrderByCreatedAtDesc(User reviewer);
}
