package com.task.tracker.exception;

import org.springframework.http.HttpStatus;

public class RefUserNotFoundException extends BaseServiceException {

    private static final long serialVersionUID = -3177060643448007226L;

    private final HttpStatus status = HttpStatus.FORBIDDEN;

    private static final String errorMessage = "User not found with name - %s";

    public RefUserNotFoundException(String exception) {
        super(String.format(errorMessage, exception));
    }

    public RefUserNotFoundException(String errorMessage, String userId){
        super(errorMessage+userId);
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}