package com.argus.test.service.taskhandler;

import com.argus.test.model.dto.ChangeGroupPayLoadDto;
import com.argus.test.model.entity.Group;
import com.argus.test.model.entity.Task;
import com.argus.test.model.enums.TaskType;
import com.argus.test.repository.GroupRepository;
import com.argus.test.service.interfaces.TaskHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Component
public class GroupStatusTaskHandler implements TaskHandler<ChangeGroupPayLoadDto> {

    @Autowired
    protected GroupRepository groupRepository;

    @Override
    public TaskType getType() {
        return TaskType.GROUP_STATUS;
    }

    @Override
    public Class<ChangeGroupPayLoadDto> getPayloadClass() {
        return ChangeGroupPayLoadDto.class;
    }

    @Override
    public void handle(@NotNull @Valid ChangeGroupPayLoadDto changeGroupPayLoadDto, Task task) {
        try {
            Group group = groupRepository.findById(changeGroupPayLoadDto.getGroupId())
                    .orElseThrow(() ->
                            new RuntimeException("Group not found: " + changeGroupPayLoadDto.getGroupId()));

            group.setActive(changeGroupPayLoadDto.isActive());

            groupRepository.save(group);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process GROUP_STATUS task", e);
        }
    }
}
