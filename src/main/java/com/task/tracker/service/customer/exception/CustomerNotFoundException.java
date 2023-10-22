package com.task.tracker.service.customer.exception;

import com.task.tracker.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends BaseServiceException {

    private static final long serialVersionUID = 4651352917857573354L;

    private final HttpStatus status = HttpStatus.FORBIDDEN;

    private static final String errorMessage = "Customer not found with name - %s";

    public CustomerNotFoundException(String exception) {
        super(String.format(errorMessage, exception));
    }

    public CustomerNotFoundException(String errorMessage, Long customerId){
        super(errorMessage+customerId);
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}