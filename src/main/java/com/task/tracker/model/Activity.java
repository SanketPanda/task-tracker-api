package com.task.tracker.model;

import com.task.tracker.common.ActivityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "activity")
public class Activity extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long activityId;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "driving_activity", nullable = false)
    private boolean drivingActivity = false;

    @Column(name = "activity_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityStatus activityStatus;

    @Column(name = "total_time")
    private Long totalTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticketId")
    private Ticket ticket;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    private List<Article> articles;
}
