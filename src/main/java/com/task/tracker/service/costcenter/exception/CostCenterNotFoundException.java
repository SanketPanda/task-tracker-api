package com.task.tracker.service.costcenter.exception;

import com.task.tracker.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class CostCenterNotFoundException extends BaseServiceException {

    private static final long serialVersionUID = 4651352917857573354L;

    private final HttpStatus status = HttpStatus.FORBIDDEN;

    private static final String errorMessage = "Cost-center not found with name - ";

    public CostCenterNotFoundException(String exception) {
        super(String.format(errorMessage, exception));
    }

    public CostCenterNotFoundException(String errorMessage, Long customerId){
        super(errorMessage+customerId);
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}
