package com.task.tracker.controller.activity;

import com.task.tracker.dto.activity.ActivityDTO;
import com.task.tracker.service.activity.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/activity")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @GetMapping("user-id/{userId}")
    private List<ActivityDTO> getActivityUserId(@PathVariable("userId") final String userId,
                                                @RequestParam(value = "status", required = false) final String activityStatus){
        return activityService.getActivityByUserIdAndStatus(userId, activityStatus);
    }

    @GetMapping("activity-id/{activityId}")
    private ActivityDTO getActivityId(@PathVariable("activityId") final Long activityId){
        return activityService.getActivityById(activityId);
    }

    @GetMapping("ticket-id/{ticketId}")
    private List<ActivityDTO> getActivityTicketId(@PathVariable("ticketId") final Long ticketId){
        return activityService.getActivityByTicketId(ticketId);
    }

    @PostMapping("")
    private ResponseEntity<ActivityDTO> addActivity(@RequestBody @Valid final ActivityDTO activityDTO){
        ActivityDTO newActivity = activityService.addActivity(activityDTO);
        return new ResponseEntity<>(newActivity, HttpStatus.CREATED);
    }

    @PutMapping("")
    private ResponseEntity<Void> updateActivity(@RequestBody @Valid final ActivityDTO activityDTO){
        activityService.updateActivity(activityDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("")
    private ResponseEntity<Void> deleteActivity(@RequestBody @Valid final ActivityDTO activityDTO){
        activityService.deleteActivity(activityDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
