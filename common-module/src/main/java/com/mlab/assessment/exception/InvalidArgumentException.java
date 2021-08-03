package com.mlab.assessment.exception;

import lombok.NoArgsConstructor;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@NoArgsConstructor
public class InvalidArgumentException extends RuntimeException {

    public InvalidArgumentException(String message) {
        super(message);
    }
    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
