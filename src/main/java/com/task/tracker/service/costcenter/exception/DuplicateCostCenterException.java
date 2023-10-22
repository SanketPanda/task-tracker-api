package com.task.tracker.service.costcenter.exception;

import com.task.tracker.exception.DuplicateDataException;

public class DuplicateCostCenterException extends DuplicateDataException {

    private static final long serialVersionUID = 4442076116315014858L;

    private static final String errorMessage = "Cost center already exist with name: %s for customer: %s.";

    public DuplicateCostCenterException(String costCenterTitle, String customerName) {
        super(String.format(errorMessage, costCenterTitle, customerName));
    }
}
