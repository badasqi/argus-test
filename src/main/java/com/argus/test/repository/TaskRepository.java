package com.argus.test.repository;

import com.argus.test.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query(value = " select * from argus_task where executed = false and execution_time <= now() and error_message is null " +
            "order by execution_time limit :limit " +
            "for update skip locked" , nativeQuery = true)
    List<Task> lockNextBatch(@Param("limit") int limit);
}
