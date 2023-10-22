package com.task.tracker.validation;

import com.task.tracker.common.TimeTrackerState;
import com.task.tracker.validation.annotation.ValidTimeTrackerState;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TimeTrackerStateValidator implements ConstraintValidator<ValidTimeTrackerState, String> {
	private ValidTimeTrackerState annotation;

	@Override
	public void initialize(ValidTimeTrackerState constraintAnnotation) {
		setAnnotation(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(!StringUtils.hasText(value)){
			return true;
		}
		final String message = annotation.message();
		final StringBuilder messageBuilder = new StringBuilder();
		final List<String> timeTrackerStateTypes = Arrays.stream(TimeTrackerState.values()).map(Objects::toString).collect(Collectors.toList());
		if(timeTrackerStateTypes.stream().anyMatch(value::equals)){
			return true;
		}
		context.disableDefaultConstraintViolation();
		messageBuilder.append(StringUtils.hasText(message) ? message : "Invalid time tracker state,");
		messageBuilder.append(String.format("allowed time tracker states are - %s.", timeTrackerStateTypes));
		context.buildConstraintViolationWithTemplate(messageBuilder.toString()).addConstraintViolation();
		return false;
	}

	private void setAnnotation(final ValidTimeTrackerState annotation){ this.annotation = annotation;}
}
