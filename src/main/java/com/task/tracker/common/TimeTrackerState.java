package com.task.tracker.common;

import java.util.Optional;

public enum TimeTrackerState {
	START,
	PAUSE,
	STOP,
	CONTINUE;

	public static TimeTrackerState getTimeTrackerState(final String timeTrackerState){
		return Optional.ofNullable(timeTrackerState).map(TimeTrackerState::valueOf).orElseGet(null);
	}
}
