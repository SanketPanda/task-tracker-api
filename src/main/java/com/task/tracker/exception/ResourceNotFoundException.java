package com.task.tracker.exception;

import org.springframework.http.HttpStatus;

public abstract class ResourceNotFoundException extends BaseServiceException {

    private static final long serialVersionUID = -27776203605743130L;

    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus(){return status;};
}
