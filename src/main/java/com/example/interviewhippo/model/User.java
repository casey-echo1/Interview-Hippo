package com.example.interviewhippo.model;

import com.example.interviewhippo.validation.ValidPassword;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Email;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@NonNull
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ValidPassword
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
	private UserState currentState = UserState.ANSWERING;

	@OneToMany(mappedBy = "user")
	private List<Answer> answers = new ArrayList<>();

	@OneToMany(mappedBy = "reviewer")
	private List<Review> reviews = new ArrayList<>();

	public enum UserState {
		ANSWERING,
		REVIEWING
	}


	//@Enumerated(EnumType.STRING)
	@Setter
	@Getter
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

