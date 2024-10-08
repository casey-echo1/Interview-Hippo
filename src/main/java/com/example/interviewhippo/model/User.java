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
	private String password;

	@NotBlank
	private String username;

	@NotBlank
	@Email
	@Column(unique = true)
	private String email;

	// allows us to track milestones and potentially celebrate
	// important dates (1 week, 1 month, 1 year)
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	// allows for disabling if user violates TOS
	private boolean enabled = true;

	// this ensures that "created at" field is set when a new user
	// entity is persisted to the DB
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}

	@Column(name = "is_admin")
	private boolean isAdmin = false;

	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}

}
