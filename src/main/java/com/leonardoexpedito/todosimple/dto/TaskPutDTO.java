package com.leonardoexpedito.todosimple.dto;

import com.leonardoexpedito.todosimple.models.Task;

import javax.validation.constraints.NotBlank;

public class TaskPutDTO {
    @NotBlank
    private String description;

    public String getDescription() {
        return description;
    }

    public void update(Task task) {
        task.setDescription(this.description);
    }
}
