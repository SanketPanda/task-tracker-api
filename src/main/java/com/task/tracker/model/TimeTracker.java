package com.task.tracker.model;

import com.task.tracker.common.TimeTrackerState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "time_tracker")
public class TimeTracker extends Auditable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long timeTrackerId;

	@Column(name = "time_tracker_state", nullable = false)
	@Enumerated(EnumType.STRING)
	private TimeTrackerState timeTrackerState;

	@Column(name = "timestamp", nullable = false)
	private Date timeStamp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private RefUser refUser;
}
