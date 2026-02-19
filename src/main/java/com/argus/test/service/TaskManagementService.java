package com.argus.test.service;

import com.argus.test.exception.TaskNotFoundException;
import com.argus.test.model.dto.TaskRequestDto;
import com.argus.test.model.dto.TaskResponseDto;
import com.argus.test.model.entity.Task;
import com.argus.test.repository.TaskRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskManagementService {

    private final ObjectMapper objectMapper;
    private final TaskRepository taskRepository;

    public TaskResponseDto createTask(TaskRequestDto dto) {
        Task task = new Task();
        task.setType(dto.getPayload().getType());
        task.setExecutionTime(dto.getExecutionTime());

        try {
            task.setPayload(objectMapper.writeValueAsString(dto.getPayload()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize payload", e);
        }

        taskRepository.save(task);

        return toResponseDto(task);
    }

    public List<TaskResponseDto> getAll() {
        return taskRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public TaskResponseDto getById(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return toResponseDto(task);
    }

    public void delete(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }

    protected TaskResponseDto toResponseDto(Task task) {
        return new TaskResponseDto(
                task.getId(),
                task.getType(),
                task.getExecutionTime(),
                task.isExecuted()
        );
    }
}
