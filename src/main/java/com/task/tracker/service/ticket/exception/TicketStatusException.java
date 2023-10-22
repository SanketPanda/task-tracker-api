package com.task.tracker.service.ticket.exception;

import com.task.tracker.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class TicketStatusException extends BaseServiceException {

	private static final long serialVersionUID = -3177060643448007226L;

	private final HttpStatus status = HttpStatus.FORBIDDEN;

	public TicketStatusException(String exception) {
		super(exception);
	}

	@Override
	public HttpStatus getStatus() {
		return status;
	}
}
