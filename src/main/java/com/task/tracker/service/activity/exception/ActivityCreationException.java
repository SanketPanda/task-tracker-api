package com.task.tracker.service.activity.exception;

import com.task.tracker.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class ActivityCreationException extends BaseServiceException {

    private static final long serialVersionUID = -8544661887302434227L;

    private final HttpStatus status = HttpStatus.FORBIDDEN;

    public ActivityCreationException(final String exception) {
        super(exception);
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}