package com.example.interviewhippo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(columnDefinition = "TEXT")
	private String content;

	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
	private List<Review> reviews = new ArrayList<>();

	// Convenience methods would still need to be manually implemented
	public void addReview(Review review) {
		reviews.add(review);
		review.setAnswer(this);
	}

	public void removeReview(Review review) {
		reviews.remove(review);
		review.setAnswer(null);
	}
}
