package com.mlab.assessment.model.response.book;

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
public class IssuedUser {
    private long id;
    private String name;
}
