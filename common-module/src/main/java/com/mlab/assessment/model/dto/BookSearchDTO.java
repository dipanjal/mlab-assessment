package com.mlab.assessment.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Getter
@Setter
@Builder
public class BookSearchDTO {
    @JsonProperty("book_name")
    private String bookName;
    @JsonProperty("author_name")
    private String authorName;
}
