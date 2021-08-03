package com.mlab.assessment.model.request.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Getter
@Setter
public class CreateBookDTO {

    @NotBlank(message = "validation.constraints.bookName.NotNull.message")
    private String name;

    @JsonProperty("author_name")
    @NotBlank(message = "validation.constraints.authorName.NotNull.message")
    private String authorName;

    @NotBlank(message = "validation.constraints.description.NotNull.message")
    private String description;

    @Min(value = 1, message = "validation.constraints.noOfCopy.Min.message")
    @JsonProperty("no_of_copy")
    private int noOfCopy;

    @JsonProperty("release_date")
    @NotBlank(message = "validation.constraints.releaseDate.NotNull.message")
    @Pattern(regexp = "^([0-2][0-9]|(3)[0-1])-(((0)[0-9])|((1)[0-2]))-\\d{4}$",
            message = "validation.constraints.releaseDate.Invalid.message")
    private String releaseDate;
}
