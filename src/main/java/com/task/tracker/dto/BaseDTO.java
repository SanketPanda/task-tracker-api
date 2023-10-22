package com.task.tracker.dto;

import lombok.Data;
import java.util.Date;

@Data
public class BaseDTO {
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
}
