package com.mlab.assessment.exception;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(){
        super("No Record Found");
    }
    public RecordNotFoundException(String message) {
        super(message);
    }
    public RecordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
