package com.example.interviewhippo.controller_test;


import com.example.interviewhippo.model.User;
import com.example.interviewhippo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PracticeControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Test
	@WithMockUser(username = "test@example.com", roles = "USER")
	void testPracticePageForAnsweringUser() throws Exception {
		User user = new User();
		user.setEmail("test@example.com");
		user.setCurrentState(User.UserState.ANSWERING);

		when(userService.getUserByEmail("test@example.com")).thenReturn(user);

		mockMvc.perform(get("/user/practice"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/practice"))
			.andExpect(model().attributeExists("question"))
			.andExpect(model().attribute("userState", User.UserState.ANSWERING))
			.andExpect(model().attribute("message", "Please answer the following question:"));
	}

	@Test
	@WithMockUser(username = "test@example.com", roles = "USER")
	void testPracticePageForReviewingUser() throws Exception {
		User user = new User();
		user.setEmail("test@example.com");
		user.setCurrentState(User.UserState.REVIEWING);

		when(userService.getUserByEmail("test@example.com")).thenReturn(user);

		mockMvc.perform(get("/user/practice"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/practice"))
			.andExpect(model().attributeExists("question"))
			.andExpect(model().attribute("userState", User.UserState.REVIEWING))
			.andExpect(model().attribute("message", "Please review the following answer:"));
	}
}
