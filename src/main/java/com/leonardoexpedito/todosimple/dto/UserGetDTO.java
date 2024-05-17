package com.leonardoexpedito.todosimple.dto;

import com.leonardoexpedito.todosimple.models.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserGetDTO {
    private Long id;
    private String username;
    private String password;

    public UserGetDTO() {
    }

    public UserGetDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static List<UserGetDTO> convert(List<User> userList) {
        return userList.stream().map(UserGetDTO::new).collect(Collectors.toList());
    }
}
