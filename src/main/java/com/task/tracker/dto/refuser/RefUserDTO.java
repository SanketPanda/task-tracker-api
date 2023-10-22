package com.task.tracker.dto.refuser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefUserDTO {

    private String userId;

    private String userName;

    private String password;

    private String roles;

    private Instant lastSyncDate;

    public RefUserDTO(String userId, String userName, String roles, Instant lastSyncDate){
        this.userId = userId;
        this.userName = userName;
        this.roles = roles;
        this.lastSyncDate = lastSyncDate;
    }
}
