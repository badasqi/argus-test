package com.argus.test.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class TaskRequestDto<T extends TaskPayloadDto> {

    @NotNull(message = "Task specification is required")
    @Valid
    private T payload;

    @NotNull(message = "Execution time is required")
    @FutureOrPresent(message = "Execution time must be now or in the future")
    private LocalDateTime executionTime;
}
