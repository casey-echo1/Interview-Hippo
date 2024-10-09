package com.example.interviewhippo.repository;

import com.example.interviewhippo.model.PasswordResetToken;
import com.example.interviewhippo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
	PasswordResetToken findByToken(String token);
	PasswordResetToken findByUser(User user);
}
