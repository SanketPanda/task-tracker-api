package com.task.tracker.service.ticket.exception;

import com.task.tracker.exception.ResourceNotFoundException;

public class TicketIdRequiredException extends ResourceNotFoundException {

    private static final long serialVersionUID = 6833807349229219486L;

    private static final String errorMessage = "Ticket id is required while update/delete.";

    public TicketIdRequiredException() {
        super(errorMessage);
    }
}