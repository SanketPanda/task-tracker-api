package com.task.tracker.controller.timetracker;

import com.task.tracker.dto.timetracker.TimeTrackerDTO;
import com.task.tracker.service.timetracker.TimeTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("v1/time-tracker")
public class TimeTrackerController {

	@Autowired
	TimeTrackerService timeTrackerService;

	@GetMapping("")
	private List<TimeTrackerDTO> getTimeTracker(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
	                                            @RequestParam("userId") String userId){
		return timeTrackerService.getTimeTrackerByDateAndUserId(date, userId);
	}

	@PostMapping
	private ResponseEntity<TimeTrackerDTO> addTimeTracker(@RequestBody @Valid final TimeTrackerDTO timeTrackerDTO){
		TimeTrackerDTO newTimeTrackerDTO= timeTrackerService.addTimeTracker(timeTrackerDTO);
		return new ResponseEntity<>(newTimeTrackerDTO, HttpStatus.CREATED);
	}
}
