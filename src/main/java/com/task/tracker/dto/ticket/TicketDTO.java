package com.task.tracker.dto.ticket;

import com.task.tracker.common.TicketStatus;
import com.task.tracker.dto.BaseDTO;
import com.task.tracker.dto.project.ProjectDTO;
import com.task.tracker.dto.refuser.RefUserDTO;
import com.task.tracker.validation.annotation.ValidTicketStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class TicketDTO extends BaseDTO {

    @Digits(integer = 10, fraction = 0, message = "At max 10 digit number")
    private Long ticketId;

    @NotBlank
    @Size(max = 255, message = "length should be at max 255 character")
    private String title;

    @NotBlank
    @Size(max = 255, message = "length should be at max 255 character")
    private String description;

    @Digits(integer = 10, fraction = 0, message = "At max 10 digit number")
    private Long priority;

    private boolean forAll = false;

    @ValidTicketStatus(message = "Invalid ticket status,")
    @NotNull
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String ticketStatus;

    private Date ticketClosedTime;

    @Digits(integer = 10, fraction = 0, message = "At max 10 digit number")
    @NotNull
    private Long projectId;

    private String assignedUserId;

    private String signature;

    private Boolean sentNotification = false;

    private ProjectDTO projectDTO;

    private RefUserDTO refUserDTO;

    public TicketStatus getTicketStatus(){ return TicketStatus.getTicketStatus(ticketStatus);}

    public void setTicketStatus(final String ticketStatus){this.ticketStatus = ticketStatus;}
}
