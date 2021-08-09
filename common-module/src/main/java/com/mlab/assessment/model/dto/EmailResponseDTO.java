package com.mlab.assessment.model.dto;

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
public class EmailResponseDTO {
    private String email;
    private String body;
}
