package com.task.tracker.service.costcenter.exception;

import com.task.tracker.exception.ResourceNotFoundException;

public class CostCenterIdRequiredException extends ResourceNotFoundException {

    private static final long serialVersionUID = 6833807349229219486L;

    private static final String errorMessage = "Costcenter id is required while update/delete.";

    public CostCenterIdRequiredException() {
        super(errorMessage);
    }
}
