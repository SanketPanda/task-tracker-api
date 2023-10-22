package com.task.tracker.dto.ticket;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class TicketSearchCriteriaDTO {

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    private String assignedUserId;
}
