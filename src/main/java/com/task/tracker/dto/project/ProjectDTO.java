package com.task.tracker.dto.project;

import com.task.tracker.dto.BaseDTO;
import com.task.tracker.dto.costcenter.CostCenterDTO;
import com.task.tracker.dto.customer.CustomerDTO;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProjectDTO extends BaseDTO {

    @Digits(integer = 10, fraction = 0, message = "At max 10 digit number")
    private Long projectId;

    @NotBlank
    @Size(max = 255, message = "length should be at max 255 character")
    private String title;

    @NotBlank
    @Size(max = 255, message = "length should be at max 255 character")
    private String description;

    @Digits(integer = 10, fraction = 0, message = "At max 10 digit number")
    @NotNull
    private Long customerId;

    @Digits(integer = 10, fraction = 0, message = "At max 10 digit number")
    @NotNull
    private Long costCenterId;

    private CustomerDTO customerDTO;

    private CostCenterDTO costCenterDTO;
}
