package com.task.tracker.service.timetracker;

import com.task.tracker.common.TimeTrackerState;
import com.task.tracker.dto.refuser.RefUserDTO;
import com.task.tracker.dto.timetracker.TimeTrackerDTO;
import com.task.tracker.model.RefUser;
import com.task.tracker.model.TimeTracker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TimeTrackerMapper {

	TimeTrackerMapper INSTANCE = Mappers.getMapper(TimeTrackerMapper.class);

	@Mapping(source = "refUser.userId", target = "userId")
	@Mapping(source = "refUser", target = "refUserDTO", qualifiedByName = "setRefUserDTO")
	TimeTrackerDTO toDTO(TimeTracker timeTracker);

	@Mapping(target = "timeTrackerId", ignore = true)
	@Mapping(source = "timeTrackerState", target = "timeTrackerState", qualifiedByName = "timeTrackerStateToTimeTrackerStateEnum")
	TimeTracker toEntity(TimeTrackerDTO timeTrackerDTO);

	static void toEntity(final TimeTrackerDTO timeTrackerDTO, final TimeTracker timeTracker){
		timeTracker.setTimeTrackerState(timeTrackerDTO.getTimeTrackerState());
		timeTracker.setTimeStamp(timeTrackerDTO.getTimeStamp());
	}

	@Named("setRefUserDTO")
	default RefUserDTO setRefUserDTO(RefUser refUser){
		if(refUser == null) return null;
		return new RefUserDTO(refUser.getUserId(), refUser.getUserName(), refUser.getRoles(), refUser.getLastSyncDate());
	}

	@Named("timeTrackerStateToTimeTrackerStateEnum")
	default TimeTrackerState timeTrackerStateToTimeTrackerStateEnum(String timeTrackerState){
		return TimeTrackerState.getTimeTrackerState(timeTrackerState);
	}
}
