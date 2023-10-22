package com.task.tracker.service.ticket;

import com.task.tracker.common.ActivityStatus;
import com.task.tracker.common.TicketStatus;
import com.task.tracker.dao.ProjectRepository;
import com.task.tracker.dao.RefUserRepository;
import com.task.tracker.dao.TicketRepository;
import com.task.tracker.dto.activity.ActivityDTO;
import com.task.tracker.dto.project.ProjectDTO;
import com.task.tracker.dto.ticket.TicketDTO;
import com.task.tracker.dto.ticket.TicketSearchCriteriaDTO;
import com.task.tracker.exception.RefUserNotFoundException;
import com.task.tracker.model.Project;
import com.task.tracker.model.RefUser;
import com.task.tracker.model.Ticket;
import com.task.tracker.service.activity.ActivityService;
import com.task.tracker.service.article.ArticleService;
import com.task.tracker.service.notification.EmailService;
import com.task.tracker.service.notification.WebSocketService;
import com.task.tracker.service.project.ProjectMapper;
import com.task.tracker.service.project.exception.ProjectNotFoundException;
import com.mcm.timeapp.service.ticket.exception.*;
import com.task.tracker.service.ticket.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.criteria.*;

@Service
public class TicketService {

    @Autowired
    TicketRepository tickerRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    RefUserRepository refUserRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    EmailService emailService;

    @Autowired
    ActivityService activityService;

    @Autowired
    ArticleService articleService;

    @Autowired
    WebSocketService webSocketService;

    private static final String NOTIFY_USER_TOPIC = "/user/%s/queue/private";
    private static final String TICKET_ASSIGNED_MSG = "A ticket has been assigned to you by: %s with title: %s";
    private static final String TICKET_COMPLETED_MSG = "Ticket completed by technician, please sign and close the ticket: %s";

    public List<TicketDTO> getClosedTicketsBetweenDates(final TicketSearchCriteriaDTO searchCriteriaDTO) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ticket> query = criteriaBuilder.createQuery(Ticket.class);
        Root<Ticket> root = query.from(Ticket.class);

        ParameterExpression<Date> startDateParam = criteriaBuilder.parameter(Date.class);
        ParameterExpression<Date> endDateParam = criteriaBuilder.parameter(Date.class);

        Expression<Date> ticketCreatedDate = criteriaBuilder.function("DATE", Date.class, root.get("createdDate"));

        Predicate datePredicate = criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(ticketCreatedDate, startDateParam),
                criteriaBuilder.lessThanOrEqualTo(ticketCreatedDate, endDateParam)
        );

        query.where(datePredicate);

        TypedQuery<Ticket> typedQuery = entityManager.createQuery(query);
        typedQuery.setParameter(startDateParam, searchCriteriaDTO.getStartDate(), TemporalType.DATE);
        typedQuery.setParameter(endDateParam, searchCriteriaDTO.getEndDate(), TemporalType.DATE);
        List<Ticket> results = typedQuery.getResultList();

        List<TicketDTO> ticketDTOS = results.stream().map(TicketMapper.INSTANCE::toDTO).collect(Collectors.toList());

        if(searchCriteriaDTO.getAssignedUserId() != null){
            return ticketDTOS.stream()
                    .filter(ticketDTO -> ticketDTO.getAssignedUserId().equals(searchCriteriaDTO.getAssignedUserId()))
                    .collect(Collectors.toList());
        }
        return ticketDTOS;
    }

    public TicketDTO getTicketById(final Long ticketId){
        return tickerRepository.findById(ticketId).map(TicketMapper.INSTANCE::toDTO)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: ",ticketId));
    }

    public List<TicketDTO> getTicketsByProjectId(final Long projectId, final String ticketStatus){
        if(!StringUtils.hasText(ticketStatus) || ticketStatus.equals("ALL")){
            List<TicketDTO> ticketList = tickerRepository.findByProjectId(projectId).stream().map(TicketMapper.INSTANCE::toDTO)
                    .collect(Collectors.toList());
            return ticketList;
        }
        return tickerRepository.findByProjectId(projectId).stream().map(TicketMapper.INSTANCE::toDTO)
                .filter(ticketDTO -> ticketDTO.getTicketStatus() == TicketStatus.getTicketStatus(ticketStatus))
                .collect(Collectors.toList());
    }

    public List<TicketDTO> getUnAssignedTicketList(){
        return tickerRepository.findUnAssignedTickets().stream().map(TicketMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public List<TicketDTO> getAllTicketList(final String ticketStatus){
        if(!StringUtils.hasText(ticketStatus) || ticketStatus.equals("ALL")){
            return tickerRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate")).stream().map(TicketMapper.INSTANCE::toDTO)
                    .collect(Collectors.toList());
        }
        return tickerRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate")).stream().map(TicketMapper.INSTANCE::toDTO)
                .filter(ticketDTO -> ticketDTO.getTicketStatus() == TicketStatus.getTicketStatus(ticketStatus))
                .collect(Collectors.toList());
    }

    public List<TicketDTO> getUnCompletedTicketListByUser(final String userId){
        return tickerRepository.findTicketsAssignedToUserAndNotCompleted(userId).stream().map(TicketMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TicketDTO addTicket(final TicketDTO ticketDTO){
        if(ticketDTO.getTicketStatus().equals(TicketStatus.CLOSED) || ticketDTO.getTicketStatus().equals(TicketStatus.COMPLETED)
        || ticketDTO.getTicketStatus().equals(TicketStatus.INPROGRESS)){
            throw new TicketStatusException("Invalid ticket status, ticket can not be created with status as CLOSED, COMPLETED or INPROGRESS");
        }
        if(ticketDTO.getTicketStatus().equals(TicketStatus.ASSIGNED) && ticketDTO.getAssignedUserId() == null){
            throw new TicketStatusException("Ticket status can not be set as ASSIGNED without assigning to a user, please set assignedUserId value");
        }
        final Project project = projectRepository.findById(ticketDTO.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: "+ticketDTO.getProjectId()));
        if(isTicketExist(ticketDTO)){
            throw new DuplicateTicketException(ticketDTO.getTitle(), project.getTitle());
        }
        Ticket ticket = TicketMapper.INSTANCE.toEntity(ticketDTO);
        Optional.ofNullable(ticketDTO.getAssignedUserId()).ifPresent(userId -> {
            final RefUser refUser = refUserRepository.findById(ticketDTO.getAssignedUserId())
                    .orElseThrow(() -> new RefUserNotFoundException("User not found with id: ", ticketDTO.getAssignedUserId()));
            ticket.setRefUser(refUser);
        });
        ticket.setProject(project);
        final Ticket newTicket = tickerRepository.save(ticket);
        ProjectDTO projectDTO = ProjectMapper.INSTANCE.toDTO(project);
        ticketDTO.setProjectDTO(projectDTO);
        ticketDTO.setCreatedBy(ticket.getCreatedBy());
        if(ticketDTO.getSentNotification() && ticketDTO.getTicketStatus() == TicketStatus.ASSIGNED && ticketDTO.getAssignedUserId() != null && !ticketDTO.getAssignedUserId().equals(ticketDTO.getCreatedBy())){
            //The email notification feature is disabled in timeapp
            //emailService.notifyTechnician(ticketDTO);
            webSocketService.sendMessage(String.format(NOTIFY_USER_TOPIC, ticketDTO.getAssignedUserId()), String.format(TICKET_ASSIGNED_MSG, ticketDTO.getCreatedBy(), ticketDTO.getTitle()));
        }
        return TicketMapper.INSTANCE.toDTO(newTicket);

    }

    @Transactional
    public void updateTicket(final TicketDTO ticketDTO){
        Optional.ofNullable(ticketDTO.getTicketId()).orElseThrow(TicketIdRequiredException::new);
        Ticket ticket = tickerRepository.findById(ticketDTO.getTicketId())
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: ", ticketDTO.getTicketId()));
        Project project = projectRepository.findById(ticketDTO.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: ", ticketDTO.getProjectId()));
        ticket.setProject(project);
        if((ticketDTO.getTicketStatus().equals(TicketStatus.ASSIGNED) || ticketDTO.getTicketStatus().equals(TicketStatus.INPROGRESS)
             || ticketDTO.getTicketStatus().equals(TicketStatus.COMPLETED) || ticketDTO.getTicketStatus().equals(TicketStatus.CLOSED)) && ticketDTO.getAssignedUserId() == null){
            throw new TicketStatusException("Ticket status can not be set as ASSIGNED/INPROGRESS/COMPLETED/CLOSED without assigning to a user, please set assignedUserId value");
        }
        if(ticketDTO.getAssignedUserId() != null){
            final RefUser refUser = refUserRepository.findById(ticketDTO.getAssignedUserId())
                    .orElseThrow(() -> new RefUserNotFoundException("User not found with id: ", ticketDTO.getAssignedUserId()));
            ticket.setRefUser(refUser);
        }else{
            ticket.setRefUser(null);
        }
        if(ticketDTO.getTicketStatus().equals(TicketStatus.COMPLETED)){
            List<ActivityDTO> activityDTOList = activityService.getActivityByTicketId(ticketDTO.getTicketId());
            activityDTOList.forEach(activityDTO -> {
                if(!activityDTO.getActivityStatus().equals(ActivityStatus.COMPLETED)){
                    throw new TicketStatusException(
                    String.format("Ticket can not be moved to COMPLETED status, activity: %s is in %s status, please move the activity status to COMPLETED",
                            activityDTO.getDescription(), activityDTO.getActivityStatus()));
                }
            });
            ticketDTO.setTicketClosedTime(new Date());
        }
        if(ticketDTO.getTicketStatus() == TicketStatus.CLOSED && ticketDTO.getSignature() == null){
            throw new SignatureRequiredException();
        }
        ProjectDTO projectDTO = ProjectMapper.INSTANCE.toDTO(project);
        ticketDTO.setProjectDTO(projectDTO);
        //The email notification feature is disabled in timeapp
        if(ticketDTO.getSentNotification() && ticketDTO.getTicketStatus() == TicketStatus.ASSIGNED &&
                ticketDTO.getAssignedUserId() != null && !ticketDTO.getAssignedUserId().equals(ticketDTO.getCreatedBy())){
            //emailService.notifyTechnician(ticketDTO);
            webSocketService.sendMessage(String.format(NOTIFY_USER_TOPIC, ticketDTO.getAssignedUserId()), String.format(TICKET_ASSIGNED_MSG, ticketDTO.getCreatedBy(), ticketDTO.getTitle()));
        }
        if(ticketDTO.getSentNotification() && ticketDTO.getTicketStatus() == TicketStatus.COMPLETED &&
                ticketDTO.getTicketId() != null && !ticketDTO.getAssignedUserId().equals(ticketDTO.getCreatedBy())){
//            List<ActivityDTO> activityDTOList = activityService.getActivityByTicketId(ticketDTO.getTicketId());
//            List<ArticleDTO> articleList = new ArrayList<>();
//            activityDTOList.forEach(activityDTO -> {
//                articleList.addAll(articleService.getArticleByActivityId(activityDTO.getActivityId()));
//            });
            //emailService.sendTicketReport(ticketDTO, activityDTOList, articleList);
            webSocketService.sendMessage(String.format(NOTIFY_USER_TOPIC, ticketDTO.getCreatedBy()), String.format(TICKET_COMPLETED_MSG, ticketDTO.getTitle()));
        }
        TicketMapper.toEntity(ticketDTO, ticket);
    }

    @Transactional
    public void deleteTicket(final TicketDTO ticketDTO){
        Optional.ofNullable(ticketDTO.getTicketId()).orElseThrow(TicketIdRequiredException::new);
        Ticket ticket = tickerRepository.findById(ticketDTO.getTicketId())
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: ", ticketDTO.getTicketId()));
        tickerRepository.delete(ticket);
    }

    private boolean isTicketExist(final TicketDTO ticketDTO){
        return tickerRepository.findByTitleAndProject(ticketDTO.getTitle(), ticketDTO.getProjectId()).isPresent();
    }
}
