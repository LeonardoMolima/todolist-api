package com.leonardoexpedito.todosimple.dto;

import com.leonardoexpedito.todosimple.models.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserPutDTO {
    @NotBlank
    @Size(min = 2, max = 100)
    private String username;

    @NotBlank
    @Size(min = 8,max = 100)
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void update(User user) {
        user.setUsername(this.username);
        user.setPassword(this.password);
    }
}
