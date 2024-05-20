package com.leonardoexpedito.todosimple.dto;

import com.leonardoexpedito.todosimple.anotations.UserFK;
import com.leonardoexpedito.todosimple.models.Task;
import com.leonardoexpedito.todosimple.models.User;
import com.leonardoexpedito.todosimple.repositories.UserRepository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public class TaskPostDTO {

    @UserFK
    Long user_id;
    @NotBlank
    private String description;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task convert(UserRepository userRepository){
        Task ret = new Task();
        Optional<User> search = userRepository.findById(this.user_id);
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