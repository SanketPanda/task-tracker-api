package com.task.tracker.service.activity;

import com.task.tracker.common.ActivityStatus;
import com.task.tracker.common.TicketStatus;
import com.task.tracker.dao.ActivityRepository;
import com.task.tracker.dao.TicketRepository;
import com.task.tracker.dto.activity.ActivityDTO;
import com.task.tracker.model.Activity;
import com.task.tracker.model.RefUser;
import com.task.tracker.model.Ticket;
import com.mcm.timeapp.service.activity.exception.*;
import com.task.tracker.service.activity.exception.ActivityNotFoundException;
import com.task.tracker.service.ticket.TicketMapper;
import com.task.tracker.service.ticket.exception.TicketNotFoundException;
import com.task.tracker.service.activity.exception.ActivityCreationException;
import com.task.tracker.service.activity.exception.ActivityIdRequiredException;
import com.task.tracker.service.activity.exception.InvalidTotalTimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    EntityManager entityManager;

    public ActivityDTO getActivityById(final Long activityId){
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new ActivityNotFoundException("Activity not found with id: %s.", activityId));
        return ActivityMapper.INSTANCE.toDTO(activity);
    }

    public List<ActivityDTO> getActivityByTicketId(final Long ticketId){
        return activityRepository.findByTicket(ticketId).stream().map(ActivityMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    public List<ActivityDTO> getActivityByUserIdAndStatus(final String userId, String activityStatus){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Activity> query = criteriaBuilder.createQuery(Activity.class);
        Root<Activity> activityRoot = query.from(Activity.class);
        Join<Activity, Ticket> ticketJoin = activityRoot.join("ticket");
        Join<Ticket, RefUser> userJoin = ticketJoin.join("refUser");

        Predicate userIdPredicate = criteriaBuilder.equal(userJoin.get("userId"), userId);

        if (StringUtils.hasText(activityStatus)) {
            String[] statusArray = activityStatus.split(",");
            Expression<ActivityStatus> statusExpression = activityRoot.get("activityStatus");
            Predicate[] statusPredicates = new Predicate[statusArray.length];
            for (int i = 0; i < statusArray.length; i++) {
                statusPredicates[i] = criteriaBuilder.equal(statusExpression, ActivityStatus.valueOf(statusArray[i]));
            }
            Predicate statusPredicate = criteriaBuilder.or(statusPredicates);
            query.where(userIdPredicate, statusPredicate);
        } else {
            query.where(userIdPredicate);
        }

        query.select(activityRoot);
        query.orderBy(criteriaBuilder.desc(activityRoot.get("createdDate")));
        List<Activity> activities = entityManager.createQuery(query).getResultList();

        return activities.stream().map(ActivityMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public ActivityDTO addActivity(final ActivityDTO activityDTO){
        Ticket ticket = ticketRepository.findById(activityDTO.getTicketId())
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: ", activityDTO.getTicketId()));
        isValidTicket(activityDTO);
        Activity activity = ActivityMapper.INSTANCE.toEntity(activityDTO);
        activity.setTicket(ticket);
        Activity newActivity = activityRepository.save(activity);
        return ActivityMapper.INSTANCE.toDTO(newActivity);
    }

    @Transactional
    public void updateActivity(final ActivityDTO activityDTO){
        Optional.ofNullable(activityDTO.getActivityId()).orElseThrow(ActivityIdRequiredException::new);
        Activity activity = activityRepository.findById(activityDTO.getActivityId())
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found with id: %s.", activityDTO.getActivityId()));
        if(activityDTO.getActivityStatus() == ActivityStatus.COMPLETED && (activityDTO.getTotalTime() == null || activityDTO.getTotalTime().equals("00:00:00"))){
            throw new InvalidTotalTimeException("Invalid activity total time: "+activityDTO.getTotalTime());
        }
        ActivityMapper.toEntity(activityDTO, activity);
        if(activityDTO.getTicketDTO() != null && activityDTO.getTicketDTO().getTicketStatus() == TicketStatus.COMPLETED){
            Ticket ticket = ticketRepository.findById(activityDTO.getTicketId())
                    .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: ", activityDTO.getTicketId()));
            TicketMapper.toEntity(activityDTO.getTicketDTO(), ticket);
        }
    }

    @Transactional
    public void deleteActivity(final ActivityDTO activityDTO){
        Optional.ofNullable(activityDTO.getActivityId()).orElseThrow(ActivityIdRequiredException::new);
        Activity activity = activityRepository.findById(activityDTO.getActivityId())
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found with id: %s.", activityDTO.getActivityId()));
        activityRepository.delete(activity);
    }

    private boolean isActivityExist(final ActivityDTO activityDTO){
        return activityRepository.findByTicketAndDrivingActivityAndStatus(activityDTO.getTicketId(), activityDTO.isDrivingActivity(), activityDTO.getActivityStatus().name()).isPresent();
    }

    private void isValidTicket(final ActivityDTO activityDTO){
        Ticket ticket = ticketRepository.findById(activityDTO.getTicketId())
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: ", activityDTO.getTicketId()));
        if(ticket.getRefUser() == null || ticket.getRefUser().getUserId() == null){
            throw new ActivityCreationException("Ticket is not assigned to a user, activity can not be created.");
        }
        if(ticket.getTicketStatus() == TicketStatus.COMPLETED || ticket.getTicketStatus() == TicketStatus.CLOSED){
            throw new ActivityCreationException("Ticket is in COMPLETED state, activity can not be created.");
        }
    }
}
