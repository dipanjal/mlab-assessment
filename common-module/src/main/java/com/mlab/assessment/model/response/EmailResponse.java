package com.mlab.assessment.model.response;

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
public class EmailResponse {
    private String email;
    private String body;
}
