package com.task.tracker.validation.annotation;

import com.task.tracker.validation.ActivityStatusValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {ActivityStatusValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidActivityStatus {

    String message() default "Invalid Activity Status.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
