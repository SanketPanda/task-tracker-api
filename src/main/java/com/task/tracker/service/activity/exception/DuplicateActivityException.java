package com.task.tracker.service.activity.exception;

import com.task.tracker.exception.DuplicateDataException;

public class DuplicateActivityException extends DuplicateDataException {

    private static final long serialVersionUID = 3624123437994729937L;

    public DuplicateActivityException(String message, String ticketTitle, boolean isDrivingActivity, String activityStatus) {
        super(String.format(message, ticketTitle, isDrivingActivity, activityStatus));
    }
}
