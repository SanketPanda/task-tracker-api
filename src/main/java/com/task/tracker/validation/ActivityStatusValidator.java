package com.task.tracker.validation;

import com.task.tracker.common.ActivityStatus;
import com.task.tracker.validation.annotation.ValidActivityStatus;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ActivityStatusValidator implements ConstraintValidator<ValidActivityStatus, String> {

    private ValidActivityStatus annotation;

    @Override
    public void initialize(ValidActivityStatus constraintAnnotation) {
        setAnnotation(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(!StringUtils.hasText(value)){
            return true;
        }
        final String message = annotation.message();
        final StringBuilder messageBuilder = new StringBuilder();
        final List<String> activityStatusTypes = Arrays.stream(ActivityStatus.values()).map(Objects::toString).collect(Collectors.toList());
        if(activityStatusTypes.stream().anyMatch(value::equals)){
            return true;
        }
        context.disableDefaultConstraintViolation();
        messageBuilder.append(StringUtils.hasText(message) ? message : "Invalid activity status,");
        messageBuilder.append(String.format("allowed activity status are - %s.", activityStatusTypes));
        context.buildConstraintViolationWithTemplate(messageBuilder.toString()).addConstraintViolation();
        return false;
    }

    private void setAnnotation(final ValidActivityStatus annotation){ this.annotation = annotation;}
}
