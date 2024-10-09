package com.example.interviewhippo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import jakarta.validation.constraints.Email;
import java.sql.Timestamp;


@Data
@Entity
@NonNull
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String password;

	private String username;

	@Email(message = "Please provide a valid email address")
	private String email;

	// allows us to track milestones and potentially celebrate
	// important dates (1 week, 1 month, 1 year)
	private Timestamp createdAt;

	// allows for disabling if user violates TOS
	private boolean enabled = true;


	@Enumerated(EnumType.STRING)
	private Role role;

}


//	// this ensures that "created at" field is set when a new user
//	// entity is persisted to the DB
//	@PrePersist
//	protected void onCreate() {
//		private
//		createdAt = LocalDateTime.now();
//	}

//	private boolean isAdmin = false;
//
//	public boolean isAdmin() {
//		return isAdmin;
//	}
//	public void setAdmin(boolean admin) {
//		isAdmin = admin;
//	}
