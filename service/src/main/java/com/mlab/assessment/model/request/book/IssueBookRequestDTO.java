package com.mlab.assessment.model.request.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import javax.validation.constraints.Min;
import java.util.Set;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Getter
@Setter
public class IssueBookRequestDTO {
    @Min(value = 1, message = "validation.constraints.userId.NotNull.message")
    @JsonProperty("user_id")
    private long userId;
    @JsonProperty("book_ids")
    @Singular
    private Set<Long> bookIds;
}
