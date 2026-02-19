package com.argus.test.service.interfaces;

import com.argus.test.model.entity.Task;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface TaskExecutor {
    void execute(Task task) throws JsonProcessingException;
}
