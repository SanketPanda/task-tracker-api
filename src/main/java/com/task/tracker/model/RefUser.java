package com.task.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ref_user")
public class RefUser {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_roles", nullable = false)
    private String roles;

    @Column(name = "last_sync_date", nullable = false)
    private Instant lastSyncDate;
}
