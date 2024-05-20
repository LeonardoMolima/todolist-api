package com.leonardoexpedito.todosimple.repositories;

import com.leonardoexpedito.todosimple.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
        User findByUsername(String username);
}
