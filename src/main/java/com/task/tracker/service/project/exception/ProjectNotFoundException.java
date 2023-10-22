package com.task.tracker.service.project.exception;

import com.task.tracker.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class ProjectNotFoundException extends BaseServiceException {

    private static final long serialVersionUID = -3177060643448007226L;

    private final HttpStatus status = HttpStatus.FORBIDDEN;

    private static final String errorMessage = "Project not found with name - ";

    public ProjectNotFoundException(String exception) {
        super(String.format(errorMessage, exception));
    }

    public ProjectNotFoundException(String errorMessage, Long customerId){
        super(errorMessage+customerId);
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}
