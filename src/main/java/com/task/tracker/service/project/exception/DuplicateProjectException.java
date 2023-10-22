package com.task.tracker.service.project.exception;

import com.task.tracker.exception.DuplicateDataException;

public class DuplicateProjectException extends DuplicateDataException {

    private static final long serialVersionUID = -7246376368488589887L;

    private static final String errorMessage = "Project already exist with name: %s for customer: %s.";

    public DuplicateProjectException(String projectTitle, String customerName) {
        super(String.format(errorMessage, projectTitle, customerName));
    }
}