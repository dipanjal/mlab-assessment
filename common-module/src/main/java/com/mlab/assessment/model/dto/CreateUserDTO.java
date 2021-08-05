package com.mlab.assessment.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Getter
@Setter
public class CreateUserDTO {
    @NotBlank(message = "validation.constraints.user.fullName.NotNull.message")
    @JsonProperty("full_name")
    private String fullName;
    @NotBlank(message = "validation.constraints.username.NotNull.message")
    @JsonProperty("user_name")
    private String userName;

    @NotBlank(message = "validation.constraints.user.email.empty.message")
    @Email(message = "validation.constraints.user.email.Invalid.message")
    private String email;
}
