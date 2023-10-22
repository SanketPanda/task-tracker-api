package com.task.tracker.service.customer.exception;

import com.task.tracker.exception.ResourceNotFoundException;

public class CustomerIdRequiredException extends ResourceNotFoundException {

    private static final long serialVersionUID = 6833807349229219486L;

    private static final String errorMessage = "Customer id is required while update/delete.";

    public CustomerIdRequiredException() {
        super(errorMessage);
    }
}
