package com.task.tracker.service.timetracker;

import com.task.tracker.dao.RefUserRepository;
import com.task.tracker.dao.TimeTrackerRepository;
import com.task.tracker.dto.timetracker.TimeTrackerDTO;
import com.task.tracker.exception.RefUserNotFoundException;
import com.task.tracker.model.RefUser;
import com.task.tracker.model.TimeTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeTrackerService {

	@Autowired
	TimeTrackerRepository timeTrackerRepository;

	@Autowired
	RefUserRepository userRepository;

	public List<TimeTrackerDTO> getTimeTrackerByDateAndUserId(final LocalDate date, final String userId){
		List<TimeTracker> currentDateTimeTrackerList = timeTrackerRepository.getTimeTrackerByDateAndUserId(date, userId);
		return currentDateTimeTrackerList.stream().map(TimeTrackerMapper.INSTANCE::toDTO).collect(Collectors.toList());
	}

	@Transactional
	public TimeTrackerDTO addTimeTracker(final TimeTrackerDTO timeTrackerDTO){
		RefUser refUser = userRepository.findById(timeTrackerDTO.getUserId()).orElseThrow(() -> new RefUserNotFoundException("User not found with id: ", timeTrackerDTO.getUserId()));
		final TimeTracker timeTracker = TimeTrackerMapper.INSTANCE.toEntity(timeTrackerDTO);
		timeTracker.setRefUser(refUser);
		final TimeTracker newTimeTracker = timeTrackerRepository.save(timeTracker);
		return TimeTrackerMapper.INSTANCE.toDTO(newTimeTracker);
	}
}
