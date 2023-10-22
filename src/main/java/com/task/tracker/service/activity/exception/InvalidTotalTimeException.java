package com.task.tracker.service.activity.exception;

import com.task.tracker.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class InvalidTotalTimeException extends BaseServiceException {

    private static final long serialVersionUID = 2024294238316224264L;

    private final HttpStatus status = HttpStatus.FORBIDDEN;

    public InvalidTotalTimeException(final String exception) {
        super(exception);
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}