package com.example.kaffeehaus.orders.entity;

import com.example.kaffeehaus.orders.control.OrderValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@NotNull
@Constraint(validatedBy = OrderValidator.class)
@Documented
public @interface ValidOrder {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
