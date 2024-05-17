package com.leonardoexpedito.todosimple.controllers;

import com.leonardoexpedito.todosimple.dto.UserGetDTO;
import com.leonardoexpedito.todosimple.dto.UserPostDTO;
import com.leonardoexpedito.todosimple.dto.UserPutDTO;
import com.leonardoexpedito.todosimple.models.User;
import com.leonardoexpedito.todosimple.repositories.UserRepository;
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
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserGetDTO> findAll() {
        List<User> userList = userRepository.findAll();
        return UserGetDTO.convert(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        ResponseEntity<User> ret = ResponseEntity.notFound().build();
        Optional<User> search = userRepository.findById(id);
        if (search.isPresent()) {
            User objUser = search.get();
            ret = ResponseEntity.ok(objUser);
        } else {
            throw new RuntimeException("Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName());
        }
        return ret;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserGetDTO> create(@Valid @RequestBody UserPostDTO obj, UriComponentsBuilder uriBuilder) {
        ResponseEntity<UserGetDTO> ret = ResponseEntity.unprocessableEntity().build();
        User objUser = obj.convert();
        Optional<User> search = userRepository.findByUsername(objUser.getUsername());
        if (!search.isPresent()) {
            userRepository.save(objUser);
            URI uri = uriBuilder.path("/{id}").buildAndExpand(objUser.getId()).toUri();
            ret = ResponseEntity.created(uri).body(new UserGetDTO(objUser));
        } else {
            throw new RuntimeException("O Nome de usuário: " + obj.getUsername() + " já existe no sistema, Tipo: " + User.class.getName());
        }
        return ret;
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UserGetDTO> update(@Valid @RequestBody UserPutDTO obj, @PathVariable("id") Long id, UriComponentsBuilder uriBuilder) {
        ResponseEntity<UserGetDTO> ret = ResponseEntity.notFound().build();
        Optional<User> search = userRepository.findById(id);
        if (search.isPresent()) {
            User objUser = search.get();
            obj.update(objUser);
            URI uri = uriBuilder.path("/user/{id}").buildAndExpand(objUser.getId()).toUri();
            ret = ResponseEntity.created(uri).body(new UserGetDTO(objUser));
        } else {
            throw new RuntimeException("Usuário não encontrado, Tipo: " + User.class.getName());
        }
        return ret;
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        ResponseEntity<User> ret = ResponseEntity.notFound().build();
        Optional<User> search = userRepository.findById(id);
        if (search.isPresent()) {
            User objUser = search.get();
            userRepository.delete(objUser);
            ret = ResponseEntity.ok().build();
        } else {
            throw new RuntimeException("Usuário não encontrado, Tipo: " + User.class.getName());
        }
        return ret;
    }

}
