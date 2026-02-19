package com.argus.test.service.interfaces;

import com.argus.test.model.dto.TaskPayloadDto;
import com.argus.test.model.entity.Task;
import com.argus.test.model.enums.TaskType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface TaskHandler<T extends TaskPayloadDto> {

    TaskType getType();

    Class<T> getPayloadClass();

    void handle(@NotNull @Valid T payLoad, Task task);
}
