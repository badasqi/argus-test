package com.argus.test.service;

import com.argus.test.model.entity.Task;
import com.argus.test.repository.TaskRepository;
import com.argus.test.service.interfaces.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class TaskSchedulerService {

    protected static final int BATCH_SIZE = 100;
    protected final ScheduledExecutorService executor;
    protected final TaskRepository taskRepository;
    protected final TaskExecutor taskExecutor;
    protected final TaskManagementService taskManagementService;

    public TaskSchedulerService(ScheduledExecutorService executor,
                                TaskRepository taskRepository,
                                TaskExecutor taskExecutor,
                                TaskManagementService taskManagementService) {
        this.executor = executor;
        this.taskRepository = taskRepository;
        this.taskExecutor = taskExecutor;
        this.taskManagementService = taskManagementService;
    }

    /**
     * Обрабатываем невыполненные задачи пачками с блокировкой.
     */
    @Scheduled(fixedDelayString = "${tasks.scheduler.fixed-delay}")
    @Transactional
    public void reschedulePendingTasks() {
        while (true) {
            List<Task> batch = taskRepository.lockNextBatch(BATCH_SIZE);
            if (batch.isEmpty()) break;

            for (Task task : batch) {
                executeTask(task);
            }
        }
    }

    private void executeTask(Task task) {
        try {
            taskExecutor.execute(task);
            task.setExecuted(true);
        } catch (Exception e) {
            task.setExecuted(false);
            task.setErrorMessage(e.getMessage());
        }

        taskRepository.save(task);
    }

}

