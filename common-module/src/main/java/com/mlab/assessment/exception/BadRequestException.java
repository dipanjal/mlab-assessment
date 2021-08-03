package com.mlab.assessment.exception;

import lombok.NoArgsConstructor;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@NoArgsConstructor
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
