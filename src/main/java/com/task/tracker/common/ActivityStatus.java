package com.task.tracker.common;

import java.util.Optional;

public enum ActivityStatus {
    ASSIGNED,
    INPROGRESS,
    PAUSED,
    COMPLETED;

    public static ActivityStatus getActivityStatus(final String activityStatus){
        return Optional.ofNullable(activityStatus).map(ActivityStatus::valueOf).orElseGet(null);
    }
}
