package com.mlab.assessment.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Getter
@Setter
public class UpdateUserDTO {
    @Min(value = 1, message = "validation.constraints.userId.NotNull.message")
    private long id;
    @NotBlank(message = "validation.constraints.user.fullName.NotNull.message")
    @JsonProperty("full_name")
    private String fullName;
    @NotBlank(message = "validation.constraints.username.NotNull.message")
    @JsonProperty("user_name")
    private String userName;
}
