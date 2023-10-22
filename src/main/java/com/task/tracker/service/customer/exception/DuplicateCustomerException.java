package com.task.tracker.service.customer.exception;

import com.task.tracker.exception.DuplicateDataException;

public class DuplicateCustomerException extends DuplicateDataException {

    private static final long serialVersionUID = 7348887131079348579L;

    private static final String errorMessage = "Company already exist with name: %s.";

    public DuplicateCustomerException(String message) {
        super(String.format(errorMessage, message));
    }
}
