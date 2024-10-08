package com.example.interviewhippo.repository;

import com.example.interviewhippo.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
