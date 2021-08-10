package com.mlab.assessment.model.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookSearchRequest {
    @JsonProperty("book_name")
    private String bookName;
    @JsonProperty("author_name")
    private String authorName;
}
