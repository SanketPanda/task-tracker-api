package com.task.tracker.dto.timetracker;

import com.task.tracker.common.TimeTrackerState;
import com.task.tracker.dto.BaseDTO;
import com.task.tracker.dto.refuser.RefUserDTO;
import com.task.tracker.validation.annotation.ValidTimeTrackerState;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class TimeTrackerDTO extends BaseDTO {

	@Digits(integer = 10, fraction = 0, message = "At max 10 digit number")
	private Long timeTrackerId;

	@ValidTimeTrackerState(message = "Invalid time tracker state,")
	@NotBlank
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private String timeTrackerState;

	@NotNull
	private Date timeStamp;

	@NotBlank
	private String userId;

	private RefUserDTO refUserDTO;

	public TimeTrackerState getTimeTrackerState(){ return TimeTrackerState.getTimeTrackerState(timeTrackerState);}

	public void setTimeTrackerState(final String timeTrackerState){this.timeTrackerState = timeTrackerState;}
}
