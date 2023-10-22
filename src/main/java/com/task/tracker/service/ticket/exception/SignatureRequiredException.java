package com.task.tracker.service.ticket.exception;

import com.task.tracker.exception.ResourceNotFoundException;

public class SignatureRequiredException extends ResourceNotFoundException {

	private static final long serialVersionUID = 6833807349229219486L;

	private static final String errorMessage = "Signature is required while closing the ticket.";

	public SignatureRequiredException() {
		super(errorMessage);
	}
}
