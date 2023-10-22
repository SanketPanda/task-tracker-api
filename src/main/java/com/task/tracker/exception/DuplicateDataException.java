package com.task.tracker.exception;

import org.springframework.http.HttpStatus;

public abstract class DuplicateDataException extends BaseServiceException {

    private static final long serialVersionUID = 5140612926929157935L;

    private final HttpStatus status = HttpStatus.CONFLICT;

    public DuplicateDataException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus(){return status;};
}
