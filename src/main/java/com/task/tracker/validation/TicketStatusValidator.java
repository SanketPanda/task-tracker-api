package com.task.tracker.validation;

import com.task.tracker.common.TicketStatus;
import com.task.tracker.validation.annotation.ValidTicketStatus;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TicketStatusValidator implements ConstraintValidator<ValidTicketStatus, String> {

    private ValidTicketStatus annotation;

    @Override
    public void initialize(ValidTicketStatus constraintAnnotation) {
        setAnnotation(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(!StringUtils.hasText(value)){
            return true;
        }
        final String message = annotation.message();
        final StringBuilder messageBuilder = new StringBuilder();
        final List<String> ticketStatusTypes = Arrays.stream(TicketStatus.values()).map(Objects::toString).collect(Collectors.toList());
        if(ticketStatusTypes.stream().anyMatch(value::equals)){
            return true;
        }
        context.disableDefaultConstraintViolation();
        messageBuilder.append(StringUtils.hasText(message) ? message : "Invalid ticket status,");
        messageBuilder.append(String.format("allowed ticket status are - %s.", ticketStatusTypes));
        context.buildConstraintViolationWithTemplate(messageBuilder.toString()).addConstraintViolation();
        return false;
    }

    private void setAnnotation(final ValidTicketStatus annotation){ this.annotation = annotation;}
}