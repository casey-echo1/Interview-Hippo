package com.example.interviewhippo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
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
}
