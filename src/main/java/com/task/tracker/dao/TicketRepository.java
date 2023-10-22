package com.task.tracker.dao;

import com.task.tracker.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.title = ?1 AND t.project.projectId = ?2 ORDER BY t.createdDate DESC")
    Optional<Ticket> findByTitleAndProject(final String title, final Long projectId);

    @Query("SELECT t FROM Ticket t WHERE t.project.projectId = ?1 ORDER BY t.modifiedDate DESC")
    List<Ticket> findByProjectId(final Long projectId);

    @Query("SELECT t FROM Ticket t WHERE t.refUser.userId IS NULL AND CAST(t.ticketStatus AS string) = 'CREATED' ORDER BY t.createdDate DESC")
    List<Ticket> findUnAssignedTickets();

    @Query("SELECT t FROM Ticket t WHERE t.refUser.userId = ?1 AND CAST(t.ticketStatus AS string) NOT IN ('CLOSED', 'COMPLETED') ORDER BY t.createdDate DESC")
    List<Ticket> findTicketsAssignedToUserAndNotCompleted(final String userId);

    @Query("SELECT t FROM Ticket t WHERE DATE_TRUNC(t.createdDate) >= DATE_TRUNC(:startDate) AND DATE_TRUNC(t.createdDate) <= DATE_TRUNC(:endDate)")
    List<Ticket> findClosedTicketsBetweenDates(Date startDate, Date endDate);
}
