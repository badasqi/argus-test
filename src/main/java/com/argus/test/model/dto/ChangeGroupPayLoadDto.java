package com.argus.test.model.dto;

import com.argus.test.model.enums.TaskType;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class ChangeGroupPayLoadDto extends TaskPayloadDto {

    @NotNull
    private UUID groupId;
    @NotNull
    private boolean active;

    @Override
    public TaskType getType() {
        return TaskType.GROUP_STATUS;
    }
}
