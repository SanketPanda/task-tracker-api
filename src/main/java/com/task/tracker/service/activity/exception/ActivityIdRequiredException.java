package com.task.tracker.service.activity.exception;

import com.task.tracker.exception.ResourceNotFoundException;

public class ActivityIdRequiredException extends ResourceNotFoundException {

    private static final long serialVersionUID = 6833807349229219486L;

    private static final String errorMessage = "Activity id is required while update/delete.";

    public ActivityIdRequiredException() {
        super(errorMessage);
    }
}
