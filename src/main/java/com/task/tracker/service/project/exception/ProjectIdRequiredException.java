package com.task.tracker.service.project.exception;

import com.task.tracker.exception.ResourceNotFoundException;

public class ProjectIdRequiredException extends ResourceNotFoundException {

    private static final long serialVersionUID = 6833807349229219486L;

    private static final String errorMessage = "Project id is required while update/delete.";

    public ProjectIdRequiredException() {
        super(errorMessage);
    }
}