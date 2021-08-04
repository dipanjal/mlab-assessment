package com.mlab.assessment.model.request.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
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
@Builder
public class BookDTO {
    @Min(value = 1, message = "Book ID is required")
    private long id;

    @NotBlank(message = "${org.hibernate.validator.constraints.NotBlank.message}")
    private String name;

    @JsonProperty("author_name")
    @NotBlank(message = "Author name can not be empty")
    private String authorName;

    @NotBlank(message = "Description can not be empty")
    private String description;

    @Min(value = 1, message = "Minimum Number of Copy is 1")
    @JsonProperty("no_of_copy")
    private int noOfCopy;

    @NotBlank(message = "Release Date can not be empty")
    @Pattern(regexp = "^([0-2][0-9]|(3)[0-1])-(((0)[0-9])|((1)[0-2]))-\\d{4}$",
            message = "Invalid Release Date, Example: 12-05-2012")
    @JsonProperty("release_date")
    private String releaseDate;
}
