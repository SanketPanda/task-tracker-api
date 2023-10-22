package com.task.tracker.service.costcenter.exception;

import com.task.tracker.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class CostCenterLinkedException extends BaseServiceException {

    private static final long serialVersionUID = -3664505888525473821L;

    private final HttpStatus status = HttpStatus.FORBIDDEN;

    private static final String errorMessage = "Cost-center can not be deleted, it is linked to project: %s.";

    public CostCenterLinkedException(String exception) {
        super(String.format(errorMessage, exception));
    }

    public CostCenterLinkedException(String errorMessage, Long customerId){
        super(errorMessage+customerId);
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}