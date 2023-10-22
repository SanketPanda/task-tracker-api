package com.task.tracker.model;

import com.task.tracker.common.TicketStatus;
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
@Table(name = "ticket")
public class Ticket extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ticketId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    private Long priority;

    private boolean forAll = false;

    @Column(name = "ticket_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus = TicketStatus.CREATED;

    @Column(name = "ticket_closed_time")
    private Date ticketClosedTime;

    @Column(columnDefinition = "TEXT")
    private String signature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private RefUser refUser;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Activity> activities;
}
