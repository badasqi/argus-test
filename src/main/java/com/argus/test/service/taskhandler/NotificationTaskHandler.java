package com.argus.test.service.taskhandler;

import com.argus.test.model.dto.NotificationPayLoadDto;
import com.argus.test.model.entity.Task;
import com.argus.test.model.enums.TaskType;
import com.argus.test.service.interfaces.TaskHandler;
import org.springframework.stereotype.Component;

import static com.argus.test.service.interfaces.ServiceConstants.DateFormats.simpleDateFormat;

@Component
public class NotificationTaskHandler implements TaskHandler<NotificationPayLoadDto> {
    @Override
    public TaskType getType() {
        return TaskType.LOG_NOTIFICATION;
    }

    @Override
    public Class<NotificationPayLoadDto> getPayloadClass() {
        return NotificationPayLoadDto.class;
    }

    @Override
    public void handle(NotificationPayLoadDto payLoad, Task task) {
        String executionTimeStr = task.getExecutionTime().format(simpleDateFormat);
        String eventTimeStr = payLoad.getEventDate().format(simpleDateFormat);

        System.out.printf("[NOTIFICATION] %s: Событие от %s — %s%n" ,
                executionTimeStr, eventTimeStr, payLoad.getNotificationText());
    }
}
