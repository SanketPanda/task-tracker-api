package com.task.tracker.service.ticket;

import com.task.tracker.common.TicketStatus;
import com.task.tracker.dto.project.ProjectDTO;
import com.task.tracker.dto.refuser.RefUserDTO;
import com.task.tracker.dto.ticket.TicketDTO;
import com.task.tracker.model.Project;
import com.task.tracker.model.RefUser;
import com.task.tracker.model.Ticket;
import com.task.tracker.service.project.ProjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketMapper {

    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    @Mapping(source = "project.projectId", target = "projectId")
    @Mapping(source = "refUser.userId", target = "assignedUserId")
    @Mapping(source = "refUser", target = "refUserDTO", qualifiedByName = "setRefUserDTO")
    @Mapping(source = "project", target = "projectDTO", qualifiedByName = "setProjectDTO")
    TicketDTO toDTO(Ticket ticket);

    @Mapping(target = "ticketId", ignore = true)
    @Mapping(source = "ticketStatus", target = "ticketStatus", qualifiedByName = "ticketStatusToTicketStatusEnum")
    Ticket toEntity(TicketDTO ticketDTO);

    static void toEntity(final TicketDTO ticketDTO, final Ticket ticket){
        ticket.setTitle(ticketDTO.getTitle());
        ticket.setDescription(ticketDTO.getDescription());
        ticket.setPriority(ticketDTO.getPriority());
        ticket.setForAll(ticketDTO.isForAll());
        ticket.setTicketStatus(ticketDTO.getTicketStatus());
        ticket.setTicketClosedTime(ticketDTO.getTicketClosedTime());
        ticket.setSignature(ticketDTO.getSignature());
    }

    @Named("setProjectDTO")
    default ProjectDTO setProjectDTO(Project project){
        return ProjectMapper.INSTANCE.toDTO(project);
    }

    @Named("setRefUserDTO")
    default RefUserDTO setRefUserDTO(RefUser refUser){
        if(refUser == null) return null;
        return new RefUserDTO(refUser.getUserId(), refUser.getUserName(), refUser.getRoles(), refUser.getLastSyncDate());
    }

    @Named("ticketStatusToTicketStatusEnum")
    default TicketStatus ticketStatusToTicketStatusEnum(String ticketStatus){
        return TicketStatus.getTicketStatus(ticketStatus);
    }

}
