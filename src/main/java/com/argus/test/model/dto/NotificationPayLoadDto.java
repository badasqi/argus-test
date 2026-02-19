package com.argus.test.model.dto;

import com.argus.test.model.enums.TaskType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class NotificationPayLoadDto extends TaskPayloadDto {

    @NotNull
    private String notificationText;

    @NotNull(message = "Execution time is required")
    @FutureOrPresent(message = "Execution time must be now or in the future")
    private LocalDate eventDate;

    @Override
    public TaskType getType() {
        return TaskType.LOG_NOTIFICATION;
    }
}
