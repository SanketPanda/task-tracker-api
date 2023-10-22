package com.task.tracker.service.activity;

import com.task.tracker.common.ActivityStatus;
import com.task.tracker.dto.activity.ActivityDTO;
import com.task.tracker.dto.ticket.TicketDTO;
import com.task.tracker.model.Activity;
import com.task.tracker.model.Ticket;
import com.task.tracker.service.ticket.TicketMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ActivityMapper {

    ActivityMapper INSTANCE = Mappers.getMapper(ActivityMapper.class);

    @Mapping(source = "ticket.ticketId", target = "ticketId")
    @Mapping(source = "ticket", target = "ticketDTO", qualifiedByName = "setTicketDTO")
    ActivityDTO toDTO(Activity activity);

    @Mapping(target = "activityId", ignore = true)
    @Mapping(source = "activityStatus", target = "activityStatus", qualifiedByName = "activityStatusToActivityStatusEnum")
    Activity toEntity(ActivityDTO activityDTO);

    static void toEntity(final ActivityDTO activityDTO, final Activity activity){
        activity.setDescription(activityDTO.getDescription());
        activity.setDrivingActivity(activityDTO.isDrivingActivity());
        activity.setActivityStatus(activityDTO.getActivityStatus());
        activity.setStartTime(activityDTO.getStartTime());
        activity.setEndTime(activityDTO.getEndTime());
        activity.setTotalTime(activityDTO.getTotalTime());
    }

    @Named("setTicketDTO")
    default TicketDTO setTicketDTO(Ticket ticket){
        return TicketMapper.INSTANCE.toDTO(ticket);
    }

    @Named("activityStatusToActivityStatusEnum")
    default ActivityStatus activityStatusToActivityStatusEnum(String activityStatus){
        return ActivityStatus.getActivityStatus(activityStatus);
    }
}
