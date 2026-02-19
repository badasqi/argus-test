package com.argus.test.service.taskhandler;

import com.argus.test.model.enums.TaskType;
import com.argus.test.service.interfaces.TaskHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class TaskHandlerRegistry {

    protected final Map<TaskType, TaskHandler<?>> handlers = new EnumMap<>(TaskType.class);

    @Autowired
    public TaskHandlerRegistry(List<TaskHandler<?>> handlerList) {
        for (TaskHandler<?> handler : handlerList) {
            handlers.put(handler.getType(), handler);
        }
    }

    public TaskHandler<?> getHandler(TaskType type) {
        return Optional.ofNullable(handlers.get(type))
                .orElseThrow(() -> new IllegalArgumentException("Unknown task type: " + type));
    }
}
