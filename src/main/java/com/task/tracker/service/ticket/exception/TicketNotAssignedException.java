package com.task.tracker.service.ticket.exception;

import com.task.tracker.exception.ResourceNotFoundException;

public class TicketNotAssignedException extends ResourceNotFoundException {

    private static final long serialVersionUID = 6723533757305774084L;

    private static final String errorMessage = "Ticket is not assigned to a user, activity can not be created";

    public TicketNotAssignedException() {
        super(errorMessage);
    }
}
