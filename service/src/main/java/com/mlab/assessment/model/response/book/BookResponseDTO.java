package com.mlab.assessment.model.response.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@Getter
@Setter
@Builder
public class BookResponseDTO {
    private long id;
    private String name;

    @JsonProperty("author_name")
    private String authorName;

    private String description;

    @JsonProperty("no_of_copy")
    private int noOfCopy;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("issued_users")
    private List<IssuedUser> issuedUsers;
}
