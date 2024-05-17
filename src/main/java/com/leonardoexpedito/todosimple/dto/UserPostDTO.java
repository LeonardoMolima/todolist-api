package com.leonardoexpedito.todosimple.dto;

import com.leonardoexpedito.todosimple.models.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserPostDTO {

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
    public User convert(){
        User ret = new User();
        ret.setUsername(this.username);
        ret.setPassword(this.password);
        return ret;
    }
}
