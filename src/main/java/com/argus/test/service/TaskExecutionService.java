package com.argus.test.service;

import com.argus.test.model.dto.TaskPayloadDto;
import com.argus.test.model.entity.Task;
import com.argus.test.service.interfaces.TaskExecutor;
import com.argus.test.service.interfaces.TaskHandler;
import com.argus.test.service.taskhandler.TaskHandlerRegistry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class TaskExecutionService implements TaskExecutor {

    protected final ObjectMapper mapper;
    protected final TaskHandlerRegistry taskHandlerRegistry;

    public TaskExecutionService(TaskHandlerRegistry taskHandlerRegistry, ObjectMapper objectMapper) {
        this.taskHandlerRegistry = taskHandlerRegistry;
        this.mapper = objectMapper;
    }

    @Override
    public void execute(Task task) throws JsonProcessingException {
        TaskHandler<?> rawHandler = taskHandlerRegistry.getHandler(task.getType());

        Class<?> payloadClass = rawHandler.getPayloadClass();

        Object payloadDto = mapper.readValue(task.getPayload(), payloadClass);

        executeHandler(rawHandler, payloadDto, task);
    }

    @SuppressWarnings("unchecked")
    private <T extends TaskPayloadDto> void executeHandler(TaskHandler<T> handler, Object payload, Task task) {
        handler.handle((T) payload, task);
    }
}
