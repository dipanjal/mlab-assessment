package com.mlab.assessment.exception;

import lombok.NoArgsConstructor;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@NoArgsConstructor
public class TransactionException extends RuntimeException {
    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
