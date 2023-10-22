package com.task.tracker.dto.article;

import com.task.tracker.dto.BaseDTO;
import com.task.tracker.dto.activity.ActivityDTO;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ArticleDTO extends BaseDTO {

    @Digits(integer = 10, fraction = 0, message = "At max 10 digit number")
    private Long articleId;

    @NotBlank
    @Size(max = 255, message = "length should be at max 255 character")
    private String articleName;

    @NotBlank
    @Size(max = 255, message = "length should be at max 255 character")
    private String articleNumber;

    @Digits(integer = 10, fraction = 0, message = "At max 10 digit number")
    @NotNull
    private Long articleCount;

    @Digits(integer = 10, fraction = 0, message = "At max 10 digit number")
    @NotNull
    private Long activityId;

    private ActivityDTO activityDTO;
}
