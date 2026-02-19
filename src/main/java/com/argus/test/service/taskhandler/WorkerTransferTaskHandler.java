package com.argus.test.service.taskhandler;

import com.argus.test.model.dto.WorkerTransferPayLoadDto;
import com.argus.test.model.entity.Group;
import com.argus.test.model.entity.Task;
import com.argus.test.model.entity.Worker;
import com.argus.test.model.enums.TaskType;
import com.argus.test.repository.GroupRepository;
import com.argus.test.repository.WorkerRepository;
import com.argus.test.service.interfaces.TaskHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkerTransferTaskHandler implements TaskHandler<WorkerTransferPayLoadDto> {

    @Autowired
    protected GroupRepository groupRepository;
    @Autowired
    protected WorkerRepository workerRepository;

    @Override
    public TaskType getType() {
        return TaskType.WORKER_TRANSFER;
    }

    @Override
    public Class<WorkerTransferPayLoadDto> getPayloadClass() {
        return WorkerTransferPayLoadDto.class;
    }

    @Override
    public void handle(WorkerTransferPayLoadDto workerTransferPayLoadDto, Task task) {
        Group tagetGroup = groupRepository
                .findById(workerTransferPayLoadDto.getGroupId())
                .orElseThrow(
                        () -> new RuntimeException("Group not found: " + workerTransferPayLoadDto.getGroupId())
                );

        Worker worker = workerRepository
                .findById(workerTransferPayLoadDto.getWorkerId())
                .orElseThrow(
                        () -> new RuntimeException("Worker not found :" + workerTransferPayLoadDto.getWorkerId())
                );

        worker.setGroup(tagetGroup);
        workerRepository.save(worker);
    }
}
