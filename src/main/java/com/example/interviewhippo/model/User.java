package com.example.interviewhippo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.NotBlank;
import javax.annotation.processing.Generated;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(unique = true)
	private String username;

	@NotBlank
	private String password;

	@NotBlank
	@Email
	private String email;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	private boolean enabled = true;

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}
}
