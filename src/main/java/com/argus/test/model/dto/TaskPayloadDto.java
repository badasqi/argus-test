package com.argus.test.model.dto;

import com.argus.test.model.enums.TaskType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = WorkerTransferPayLoadDto.class, name = "WORKER_TRANSFER"),
        @JsonSubTypes.Type(value = ChangeGroupPayLoadDto.class, name = "GROUP_STATUS"),
        @JsonSubTypes.Type(value = NotificationPayLoadDto.class, name = "LOG_NOTIFICATION")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class TaskPayloadDto implements Serializable {

    @JsonIgnore
    public abstract TaskType getType();
}
