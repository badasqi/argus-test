package com.argus.test.web.controller;

import com.argus.test.model.dto.TaskRequestDto;
import com.argus.test.model.dto.TaskResponseDto;
import com.argus.test.service.TaskManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    protected final TaskManagementService taskManagementService;

    public TaskController(TaskManagementService taskManagementService) {
        this.taskManagementService = taskManagementService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDto createTask(@Valid @RequestBody TaskRequestDto dto) {
        return taskManagementService.createTask(dto);
    }

    @GetMapping
    public List<TaskResponseDto> getAll() {
        return taskManagementService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(taskManagementService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        taskManagementService.delete(id);
    }
}