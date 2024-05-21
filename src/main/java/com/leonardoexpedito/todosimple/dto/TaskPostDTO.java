package com.leonardoexpedito.todosimple.dto;

import com.leonardoexpedito.todosimple.anotations.UserFK;
import com.leonardoexpedito.todosimple.models.Task;
import com.leonardoexpedito.todosimple.models.User;
import com.leonardoexpedito.todosimple.repositories.UserRepository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public class TaskPostDTO {
    @NotBlank
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task convert(UserRepository userRepository, String username){
        Task ret = new Task();
        Optional<User> search = Optional.ofNullable(userRepository.findByUsername(username));
        if(search.isPresent()){
            User objUser = search.get();
            ret.setUser(objUser);
            ret.setDescription(this.description);
        } else {
            ret = null;
        }
        return ret;
    }
}