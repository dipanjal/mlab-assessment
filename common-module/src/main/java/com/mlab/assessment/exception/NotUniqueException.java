package com.mlab.assessment.exception;

import lombok.NoArgsConstructor;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@NoArgsConstructor
public class NotUniqueException extends BadRequestException {

    public NotUniqueException(String message) {
        super(message);
    }
}
