package com.argus.test;

import com.argus.test.model.dto.NotificationPayLoadDto;
import com.argus.test.model.dto.TaskRequestDto;
import com.argus.test.model.dto.TaskResponseDto;
import com.argus.test.model.enums.TaskType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testCreateGetAndDeleteTask() throws Exception {
		NotificationPayLoadDto payload = new NotificationPayLoadDto();
		payload.setNotificationText("Test task");
		payload.setEventDate(LocalDate.now().plusDays(1));

		TaskRequestDto requestDto = new TaskRequestDto();
		requestDto.setExecutionTime(LocalDateTime.now().plusMinutes(5));
		requestDto.setPayload(payload);

		String jsonRequest = objectMapper.writeValueAsString(requestDto);

		String response = mockMvc.perform(post("/tasks")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.type", is("LOG_NOTIFICATION")))
				.andReturn()
				.getResponse()
				.getContentAsString();

		TaskResponseDto createdTask = objectMapper.readValue(response, TaskResponseDto.class);
		UUID taskId = createdTask.getId();

		mockMvc.perform(get("/tasks/{id}", taskId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(taskId.toString()))
				.andExpect(jsonPath("$.type").value("LOG_NOTIFICATION"));

		mockMvc.perform(get("/tasks"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());

		mockMvc.perform(delete("/tasks/{id}", taskId))
				.andExpect(status().isNoContent());
	}
}
