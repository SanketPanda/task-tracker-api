package com.task.tracker.service.ticket.exception;

import com.task.tracker.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class TicketNotFoundException extends BaseServiceException {

    private static final long serialVersionUID = -3177060643448007226L;

    private final HttpStatus status = HttpStatus.FORBIDDEN;

    private static final String errorMessage = "Ticket not found with title - ";

    public TicketNotFoundException(String exception) {
        super(String.format(errorMessage, exception));
    }

    public TicketNotFoundException(String errorMessage, Long customerId){
        super(errorMessage+customerId);
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}
