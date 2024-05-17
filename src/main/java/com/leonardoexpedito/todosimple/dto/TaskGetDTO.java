package com.leonardoexpedito.todosimple.dto;

import com.leonardoexpedito.todosimple.models.Task;

import java.util.List;
import java.util.stream.Collectors;

public class TaskGetDTO {

    private Long id;
    private String description;
    Long user_id;

    public TaskGetDTO() {
    }

    public TaskGetDTO(Task task) {
        this.id = task.getId();
        this.description = task.getDescription();
        this.user_id = task.getUser().getId();
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Long getUser_id() {
        return user_id;
    }

    public static List<TaskGetDTO> convert(List<Task> taskList) {
        return taskList.stream().map(TaskGetDTO::new).collect(Collectors.toList());
    }
}
