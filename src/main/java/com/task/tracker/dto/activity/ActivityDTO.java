package com.task.tracker.dto.activity;

import com.task.tracker.common.ActivityStatus;
import com.task.tracker.dto.BaseDTO;
import com.task.tracker.dto.ticket.TicketDTO;
import com.task.tracker.validation.annotation.ValidActivityStatus;
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
public class ActivityDTO extends BaseDTO {

    @Digits(integer = 10, fraction = 0, message = "At max 10 digit number")
    private Long activityId;

    private Date startTime;

    private Date endTime;

    @NotBlank
    @Size(max = 255, message = "length should be at max 255 character")
    private String description;

    private boolean drivingActivity = false;

    @ValidActivityStatus(message = "Invalid activity status,")
    @NotNull
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String activityStatus;

    @Digits(integer = 10, fraction = 0, message = "At max 10 digit number")
    @NotNull
    private Long ticketId;

    private Long totalTime;

    private TicketDTO ticketDTO;

    public ActivityStatus getActivityStatus(){ return ActivityStatus.getActivityStatus(activityStatus);}

    public void setActivityStatus(final String activityStatus){this.activityStatus = activityStatus;}
}
