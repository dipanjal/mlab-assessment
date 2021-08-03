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
public class TransactionRequestDTO {
    private String requestId;
    private String requester;
    private String transactionType;
    private String sourceAccountNumber;
    private double amount;
    private String destinationAccountNumber;
    private String note;
}
