package com.argus.test.model.entity;

import com.argus.test.model.enums.TaskType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "argus_task")
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskType type;

    private String payload;

    private LocalDateTime executionTime;

    private boolean executed = false;

    @Column(length = 2048)
    private String errorMessage;
}
