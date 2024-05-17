package com.leonardoexpedito.todosimple.anotations;

import com.leonardoexpedito.todosimple.validator.UserFKValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UserFKValidator.class)
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserFK {
    String message() default "Usuário Inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default  { };
}
