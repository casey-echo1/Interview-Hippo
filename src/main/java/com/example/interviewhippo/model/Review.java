package com.example.interviewhippo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "answer_id")
	private Answer answer;

	@ManyToOne
	@JoinColumn(name = "reviewer_id")
	private User reviewer;

	private int score;

	@Column(columnDefinition = "TEXT")
	private String comment;

	private LocalDateTime createdAt;
}
