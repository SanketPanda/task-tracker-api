package com.task.tracker.common;

import java.util.Optional;

public enum TicketStatus {
    CREATED,
    ASSIGNED,
    INPROGRESS,
    COMPLETED,
    CLOSED;

    public static TicketStatus getTicketStatus(final String ticketStatus){
        return Optional.ofNullable(ticketStatus).map(TicketStatus::valueOf).orElseGet(null);
    }
}
