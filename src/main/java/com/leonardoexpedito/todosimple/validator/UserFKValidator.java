package com.leonardoexpedito.todosimple.validator;

import com.leonardoexpedito.todosimple.anotations.UserFK;
import com.leonardoexpedito.todosimple.models.User;
import com.leonardoexpedito.todosimple.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UserFKValidator implements ConstraintValidator<UserFK, Long> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value != null) {
            Optional<User> search = userRepository.findById(value);
            if (search.isPresent()) {
                return true;
            }
        }
        return false;
    }
}
