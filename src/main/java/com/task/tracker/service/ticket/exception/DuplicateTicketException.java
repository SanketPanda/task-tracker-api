package com.task.tracker.service.ticket.exception;

import com.task.tracker.exception.DuplicateDataException;

public class DuplicateTicketException extends DuplicateDataException {

    private static final long serialVersionUID = -7246376368488589887L;

    private static final String errorMessage = "Ticket already exist with title: %s for project: %s.";

    public DuplicateTicketException(String ticketTitle, String projectName) {
        super(String.format(errorMessage, ticketTitle, projectName));
    }
}
