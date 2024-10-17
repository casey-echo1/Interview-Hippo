package com.example.interviewhippo.model;

import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@ManyToOne
	@JoinColumn(name = "answer_id")
	private Answer answer;

	@Setter
	@ManyToOne
	@JoinColumn(name = "reviewer_id")
	private User reviewer;

	@Setter
	private int score;

	@Setter
	@Column(columnDefinition = "TEXT")
	private String comment;

	@Setter
	private LocalDateTime createdAt;

}
