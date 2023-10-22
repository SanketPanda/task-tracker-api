package com.task.tracker.dto.customer;

import com.task.tracker.dto.BaseDTO;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CustomerDTO extends BaseDTO {

    @Digits(integer = 10, fraction = 0, message = "At max 10 digit number")
    private Long customerId;

    @NotBlank
    @Size(max = 255, message = "length should be at max 255 character")
    private String company;

    @NotBlank
    @Size(max = 255, message = "length should be at max 255 character")
    private String contactName;

    @NotBlank
    @Size(max = 255, message = "length should be at max 255 character")
    private String contactEmail;

    @NotBlank
    @Size(max = 10, message = "length should be at max 10 character")
    private String contactPhone;

    @NotBlank
    @Size(max = 255, message = "length should be at max 255 character")
    private String address;

    @NotBlank
    private String description;
}
