package com.mlab.assessment.model.response.user;

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
public class UserResponseDTO {
    private long id;
    private String name;
    @JsonProperty("user_name")
    private String userName;
    private String email;
    @JsonProperty("issued_books")
    private List<IssuedBook> issuedBooks;

}
