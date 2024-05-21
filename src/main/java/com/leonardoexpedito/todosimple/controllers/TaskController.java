package com.leonardoexpedito.todosimple.controllers;

import com.leonardoexpedito.todosimple.dto.TaskGetDTO;
import com.leonardoexpedito.todosimple.dto.TaskPostDTO;
import com.leonardoexpedito.todosimple.dto.TaskPutDTO;
import com.leonardoexpedito.todosimple.models.Task;
import com.leonardoexpedito.todosimple.repositories.TaskRepository;
import com.leonardoexpedito.todosimple.repositories.UserRepository;
import com.leonardoexpedito.todosimple.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping
    public List<TaskGetDTO> findAll(){
        List<Task> taskList = taskRepository.findAll();
        return TaskGetDTO.convert(taskList);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id){
        ResponseEntity<Task> ret = ResponseEntity.notFound().build();
        Optional<Task> search = taskRepository.findById(id);
        if(search.isPresent()){
            Task objTask = search.get();
            ret = ResponseEntity.ok(objTask);
        } else {
            throw new RuntimeException("Task não encontrada! Id: " + id + ", Tipo: " + Task.class.getName());
        }
        return ret;
    }


    @GetMapping("/user/{userId}")
    public List<TaskGetDTO> findByUserId(@PathVariable Long userId){
        ResponseEntity<Task> ret = ResponseEntity.notFound().build();
        List<Task> taskList = taskRepository.findByUser_Id(userId);
        return TaskGetDTO.convert(taskList);
    }


    @PostMapping
    @Transactional
    public ResponseEntity<TaskGetDTO> create(@Valid @RequestBody TaskPostDTO obj, UriComponentsBuilder uriBuilder,@RequestHeader("Authorization") String token){
        ResponseEntity<TaskGetDTO> ret = ResponseEntity.unprocessableEntity().build();
        token = token.replace("Bearer ", "");
        String username = authenticationService.tokenValidation(token);
        Task objTask = obj.convert(userRepository, username);
        taskRepository.save(objTask);
        URI uri = uriBuilder.path("/{id}").buildAndExpand(objTask.getId()).toUri();
        return ResponseEntity.created(uri).body(new TaskGetDTO(objTask));
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TaskGetDTO> update(@Valid @RequestBody TaskPutDTO obj, @PathVariable("id") Long id, UriComponentsBuilder uriBuilder ) {
        ResponseEntity<TaskGetDTO> ret = ResponseEntity.notFound().build();
        Optional<Task> search = taskRepository.findById(id);
        if(search.isPresent()) {
            Task objTask = search.get();
            obj.update(objTask);
            URI uri = uriBuilder.path("/task/{id}").buildAndExpand(objTask.getId()).toUri();
            ret = ResponseEntity.created(uri).body(new TaskGetDTO(objTask));
        } else {
            throw new RuntimeException("Task não encontrada, Tipo: " + Task.class.getName());
        }
        return ret;
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        ResponseEntity<Task> ret = ResponseEntity.notFound().build();
        Optional<Task> search = taskRepository.findById(id);
        if (search.isPresent()) {
            Task objTask = search.get();
            taskRepository.delete(objTask);
            ret = ResponseEntity.ok().build();
        } else {
            throw new RuntimeException("Task não encontrada, Tipo: " + Task.class.getName());
        }
        return ret;
    }

}