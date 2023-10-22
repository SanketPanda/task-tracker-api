package com.task.tracker.dao;

import com.task.tracker.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query("SELECT a FROM Activity a WHERE a.ticket.ticketId = ?1 AND a.drivingActivity = ?2 AND CAST(a.activityStatus AS string) = ?3 ORDER BY a.createdDate DESC")
    Optional<Activity> findByTicketAndDrivingActivityAndStatus(final Long ticketId, final boolean drivingActivity, final String status);

    @Query("SELECT a FROM Activity a WHERE a.ticket.ticketId = ?1 ORDER BY a.createdDate DESC")
    List<Activity> findByTicket(final Long ticketId);

    @Query("SELECT a FROM Activity a WHERE a.ticket.refUser.userId = ?1 AND CAST(a.activityStatus AS string) LIKE ?2 ORDER BY a.createdDate DESC")
    List<Activity> findByUserIdAndStatus(final String userId, final String activityStatus);
}
