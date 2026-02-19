package com.argus.test.model.dto;

import com.argus.test.model.enums.TaskType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class WorkerTransferPayLoadDto extends TaskPayloadDto {

    @NotNull
    private UUID workerId;

    @NotNull
    private UUID groupId;

    @Override
    public TaskType getType() {
        return TaskType.WORKER_TRANSFER;
    }
}
