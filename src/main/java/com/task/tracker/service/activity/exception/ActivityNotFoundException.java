package com.task.tracker.service.activity.exception;

import com.task.tracker.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class ActivityNotFoundException extends BaseServiceException {

    private static final long serialVersionUID = -3177060643448007226L;

    private final HttpStatus status = HttpStatus.FORBIDDEN;

    private static final String errorMessage = "Activity not found with name: %s.";

    public ActivityNotFoundException(String exception) {
        super(String.format(errorMessage, exception));
    }

    public ActivityNotFoundException(String errorMessage, Long activityId){
        super(String.format(errorMessage,activityId));
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}