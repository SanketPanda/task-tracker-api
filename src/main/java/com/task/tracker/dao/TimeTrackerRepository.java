package com.task.tracker.dao;

import com.task.tracker.model.TimeTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeTrackerRepository extends JpaRepository<TimeTracker, Long> {

	@Query("SELECT t FROM TimeTracker t WHERE " +
			"FUNCTION('YEAR', t.createdDate) = YEAR(:date) AND " +
			"FUNCTION('MONTH', t.createdDate) = MONTH(:date) AND " +
			"FUNCTION('DAY', t.createdDate) = DAY(:date) AND " +
			"t.refUser.userId = :userId")
	List<TimeTracker> getTimeTrackerByDateAndUserId(LocalDate date, String userId);
}
