package com.task.tracker.service.user.exception;

import com.task.tracker.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseServiceException {

    private static final long serialVersionUID = -3177060643448007226L;

    private final HttpStatus status = HttpStatus.FORBIDDEN;

    private static final String errorMessage = "User not found with id - %s.";

    public UserNotFoundException(String userId) {
        super(String.format(errorMessage, userId));
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}