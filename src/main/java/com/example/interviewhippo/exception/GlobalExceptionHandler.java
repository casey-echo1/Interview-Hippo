package com.example.interviewhippo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllExceptions(HttpServletRequest request, Exception ex) {
		logger.error("Unhandled exception occurred", ex);

		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", ex);
		mav.addObject("url", request.getRequestURL());
		mav.setViewName("error");
		return mav;
	}

	@ExceptionHandler(NoAnswersAvailableException.class)
	public String handleNoAnswersAvailableException(NoAnswersAvailableException ex, Model model) {
		model.addAttribute("errorMessage", "No answers are currently available for review. You've been redirected to answer a question.");
		return "redirect:/user/practice";
	}

	@ExceptionHandler(NoResourceFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> handleNoResourceFoundException(NoResourceFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body("Resource not found");
	}

//	@ExceptionHandler(NoResourceFoundException.class)
//	@ResponseStatus(HttpStatus.NOT_FOUND)
//	public ResponseEntity<String> handleNoResourceFoundException(NoResourceFoundException ex) {
//		return ResponseEntity.status(HttpStatus.NOT_FOUND)
//			.body("Resource not found");
//	}
}
