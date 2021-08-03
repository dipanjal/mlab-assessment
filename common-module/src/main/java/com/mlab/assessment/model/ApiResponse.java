package com.mlab.assessment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@Getter
@Builder(toBuilder = true)
public class ApiResponse<T> {
    @JsonProperty("response_code")
    private int responseCode;
    @JsonProperty("response_message")
    private String responseMessage;
    private T data;
}
